package org.btax.core.inventory;

import org.btax.core.PriceService;

import java.util.Date;

public interface InventoryTransaction {
    double priceForQuantity(double quantity);
    void transformByQuantity(double newQuantity);
    String getCurrency();
    void setCurrency(String currency);
    double getQuantity();
    Date getDate();
    void setQuantity(double quantity);
    double getPrice();
    void setPrice(double price);
    void setPrice(PriceService service) throws Exception;
    void setDate(Date date);
    double sumQuantity();
}