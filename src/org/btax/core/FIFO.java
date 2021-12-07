package org.btax.core;

import org.btax.core.inventory.IncomingInventoryTransaction;
import org.btax.core.inventory.Inventory;
import org.btax.core.inventory.InventoryTransaction;
import org.btax.core.inventory.OutgoingInventoryTransaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class FIFO {
    static final int TAXABLE_THRESHOLD_BEFORE_2019 = 5000;
    Inventory inventory;
    double gain;

    FIFO(Inventory inventory, Date limit) {
        double quantityToErase, acquisitionCost, diff;
        int inputIndex;
        List<InventoryTransaction> inventoryTransactionsResult = new ArrayList<>(inventory.getInventoryTransactions());
        InventoryTransaction inventorytransaction;

        // We 'mutate' the input inventory transactions list to erase transactions that cancel each other
        for (InventoryTransaction tx : inventoryTransactionsResult) {
            if (tx instanceof OutgoingInventoryTransaction && tx.getDate().getTime() < limit.getTime()) {
                OutgoingInventoryTransaction out = (OutgoingInventoryTransaction) tx;
                quantityToErase = out.getQuantity();
                acquisitionCost = 0;
                inputIndex = 0;

                while (quantityToErase > 0) {

                    // We should not find outbound indexes since at the beginning of the program we check for inventory overflow
                    // But some times there are still remaining dusts in the inventory when we do some calculations so we fix quantityToErase at 0
                    inventorytransaction = null;
                    try {
                        inventorytransaction = inventoryTransactionsResult.get(inputIndex);
                    } catch (IndexOutOfBoundsException exception) {
                        quantityToErase = 0;
                    }

                    if (inventorytransaction instanceof IncomingInventoryTransaction && inventorytransaction.getQuantity() > 0) {
                        IncomingInventoryTransaction in = (IncomingInventoryTransaction) inventorytransaction;
                        diff = quantityToErase - in.getQuantity();

                        if (diff < 0) {
                            acquisitionCost += in.priceForQuantity(quantityToErase);
                            in.transformByQuantity(Math.abs(diff));
                            quantityToErase = 0;
                        } else if (diff > 0) {
                            acquisitionCost += in.getCost();
                            in.transformByQuantity(0);
                            quantityToErase = diff;
                        } else {
                            acquisitionCost += in.getCost();
                            in.transformByQuantity(0);
                            quantityToErase = 0;
                        }
                    }
                    inputIndex++;
                }
                if (out.getRevenue() > TAXABLE_THRESHOLD_BEFORE_2019) {
                    gain += (out.getRevenue() - acquisitionCost);
                }
                out.transformByQuantity(0);
            }
        }

        inventoryTransactionsResult = inventoryTransactionsResult.stream()
                .filter(tx -> tx.getQuantity() > 0).collect(Collectors.toList());

        this.inventory = new Inventory(inventory.getCurrency(), inventoryTransactionsResult);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public double getGain() {
        return gain;
    }
}
