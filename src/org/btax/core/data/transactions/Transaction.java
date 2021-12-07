package org.btax.core.data.transactions;

import org.btax.core.PriceService;
import org.btax.core.inventory.InventoryCurrencyNotFoundInTransaction;
import org.btax.core.inventory.InventoryTransaction;

import java.util.Date;

public interface Transaction {
    String getExchange();
    void setExchange(String exchange);
    String getGroup();
    void setGroup(String group);
    String getComment();
    void setComment(String comment);
    Date getDate();
    void setRecordString(String recordString);
    String getRecordString();
    InventoryTransaction createInventoryTransaction(String currency, PriceService service) throws InventoryCurrencyNotFoundInTransaction;
}