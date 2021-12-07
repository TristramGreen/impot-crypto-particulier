package org.btax.core.inventory;

import java.util.Date;

public class OutgoingInventoryTransaction extends InventoryTransactionBase {
    public OutgoingInventoryTransaction() {}

    public OutgoingInventoryTransaction(String currency, Date date, double quantity, double price) {
        super(currency, date, quantity, price);
    }

    @Override
    public double sumQuantity() { return -(getQuantity()); }

    public double getRevenue() { return getPrice(); }
}