package org.btax.core;

import org.btax.core.data.transactions.HasOutgoing;
import org.btax.core.data.transactions.Transaction;
import org.btax.core.inventory.Inventory;

import java.text.DecimalFormat;

public class InventoryOverflowException extends Exception {

    Transaction transaction;
    String currency;
    double outgoing;
    double quantity;
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00000000");

    InventoryOverflowException(HasOutgoing outgoingTransaction, Inventory inventory) {
        this.transaction = outgoingTransaction;
        this.currency = outgoingTransaction.getOutgoingCurrency();
        this.outgoing = outgoingTransaction.getOutgoing();
        this.quantity = inventory.quantity();
    }

    @Override
    public String getMessage() {
        String message = "\n";
        message += "ERROR: Inventory Overflow Exception\n";
        message += transaction.getRecordString()+"\n";
        message += decimalFormat.format(this.outgoing)+" outgoing "+this.currency+" but the quantity in the inventory at this moment is "+decimalFormat.format(this.quantity)+"\n";
        message += "Difference = quantity - outgoing = "+decimalFormat.format(this.quantity-this.outgoing);
        return message;
    }
}