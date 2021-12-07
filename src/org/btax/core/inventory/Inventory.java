package org.btax.core.inventory;

import org.btax.core.Bookkeeping;
import org.btax.core.PriceService;
import org.btax.core.data.transactions.HasIncoming;
import org.btax.core.data.transactions.HasOutgoing;
import org.btax.core.data.transactions.Transaction;
import org.btax.core.data.transactions.incoming.Deposit;
import org.btax.core.data.transactions.outgoing.Withdrawal;

import java.util.*;
import java.util.stream.Collectors;

public class Inventory {
    String currency;
    List<InventoryTransaction> inventoryTransactions = new ArrayList<>();

    public Inventory(String currency, List<Transaction> transactions, PriceService service) {
        setCurrency(currency);
        setInventoryTransactionsFromTransactions(transactions, service);
    }

    public Inventory(String currency, List<InventoryTransaction> inventoryTransactions) {
        setCurrency(currency);
        setInventoryTransactions(inventoryTransactions);
    }

    public double quantity() {
        return inventoryTransactions.stream()
                .map(InventoryTransaction::sumQuantity)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double value(Date date, PriceService service) throws Exception {
        return quantity() * service.getPrice(getCurrency(), Bookkeeping.CURRENCY, date);
    }

    public double cost() {
        return inventoryTransactions.stream()
                .filter(inventoryTransaction -> inventoryTransaction instanceof IncomingInventoryTransaction)
                .map(inventoryTransaction -> (IncomingInventoryTransaction) inventoryTransaction)
                .map(IncomingInventoryTransaction::getCost)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public void setInventoryTransactionsFromTransactions(List<Transaction> transactions, PriceService service) {
        this.inventoryTransactions = transactions.stream()
                .filter(transaction -> !(transaction instanceof Deposit))
                .filter(transaction -> !(transaction instanceof Withdrawal))
                .filter(transaction -> {
                    if (transaction instanceof HasIncoming) {
                        if ( ((HasIncoming) transaction).getIncomingCurrency().equalsIgnoreCase(getCurrency()) ) return true;
                    }
                    if (transaction instanceof HasOutgoing) {
                        if ( ((HasOutgoing) transaction).getOutgoingCurrency().equalsIgnoreCase(getCurrency()) ) return true;
                    }
                    return false;
                })
                .map(transaction -> {
                    try {
                        return transaction.createInventoryTransaction(getCurrency(), service);
                    } catch (InventoryCurrencyNotFoundInTransaction inventoryCurrencyNotFoundInTransaction) {
                        inventoryCurrencyNotFoundInTransaction.printStackTrace();
                        System.exit(1);
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    public void setInventoryTransactions(List<InventoryTransaction> inventoryTransactions) {
        this.inventoryTransactions = inventoryTransactions;
    }

    public List<InventoryTransaction> getInventoryTransactions() {
        return inventoryTransactions;
    }

    public void setCurrency(String currency) {
        this.currency = currency.toUpperCase();
    }

    public String getCurrency() {
        return currency;
    }
}

