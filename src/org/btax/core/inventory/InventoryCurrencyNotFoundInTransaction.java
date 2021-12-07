package org.btax.core.inventory;

public class InventoryCurrencyNotFoundInTransaction extends Exception {
    String currency;
    public InventoryCurrencyNotFoundInTransaction(String currency) { this.currency = currency; }

    @Override
    public String getMessage() {
        return "inventory currency: '"+currency+"' not found in Trade.incomingCurrency or Trade.outgoingCurrency";
    }
}