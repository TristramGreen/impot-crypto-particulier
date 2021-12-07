package org.btax.core.inventory;

import java.util.Date;

public class IncomingInventoryTransaction extends InventoryTransactionBase {
    public IncomingInventoryTransaction() {}

    public IncomingInventoryTransaction(String currency, Date date, double quantity, double price) {
        super(currency, date, quantity, price);
    }

    public double getCost() { return getPrice(); }
}