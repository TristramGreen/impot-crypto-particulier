package org.btax.core.inventory;

import org.btax.core.Bookkeeping;
import org.btax.core.PriceService;

import java.util.Date;

public abstract class InventoryTransactionBase implements InventoryTransaction {
    protected String currency;
    protected Date date;
    protected double quantity;
    protected double price;

    public InventoryTransactionBase() {}

    public InventoryTransactionBase(String currency, Date date, double quantity, double price) {
        this.currency = currency;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
    }

    public double priceForQuantity(double quantity) {
        return getPrice() * (quantity / getQuantity());
    }

    public void transformByQuantity(double newQuantity) {
        this.setPrice(priceForQuantity(newQuantity));
        this.setQuantity(newQuantity);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getQuantity() { return quantity; }

    public Date getDate() { return date; }

    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPrice(PriceService service) throws Exception {
        this.price = service.getPrice(getCurrency(), Bookkeeping.CURRENCY, getDate());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double sumQuantity() { return getQuantity(); }
}