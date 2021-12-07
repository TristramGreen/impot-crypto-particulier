package org.btax.core.data.transactions.outgoing;

import org.btax.core.PriceService;
import org.btax.core.inventory.InventoryCurrencyNotFoundInTransaction;
import org.btax.core.inventory.InventoryTransaction;
import org.btax.core.inventory.OutgoingInventoryTransaction;
import org.btax.core.data.transactions.HasOutgoing;
import org.btax.core.data.transactions.TransactionBase;
import org.btax.core.data.transactions.TransactionFactory;
import org.btax.core.data.CointrackingCSVRow;

import java.text.ParseException;

public class Lost extends TransactionBase implements HasOutgoing {
    double outgoing;
    String outgoingCurrency;

    public Lost(CointrackingCSVRow row) throws ParseException {
        setOutgoing(TransactionFactory.makeOutgoing(row));
        setOutgoingCurrency(TransactionFactory.makeOutgoingCurrency(row));
        setExchange(TransactionFactory.makeExchange(row));
        setGroup(TransactionFactory.makeGroup(row));
        setComment(TransactionFactory.makeComment(row));
        setDate(TransactionFactory.makeDate(row));
        setRecordString(row.getRecordString());
    }

    @Override
    public InventoryTransaction createInventoryTransaction(String currency, PriceService service) throws InventoryCurrencyNotFoundInTransaction {
        OutgoingInventoryTransaction outgoingInventoryTransaction = new OutgoingInventoryTransaction();
        if (currency.equalsIgnoreCase(getOutgoingCurrency())) {
            outgoingInventoryTransaction.setCurrency(currency);
            outgoingInventoryTransaction.setPrice(0);
            outgoingInventoryTransaction.setQuantity(getOutgoing());
            outgoingInventoryTransaction.setDate(getDate());
        } else throw new InventoryCurrencyNotFoundInTransaction(currency);
        return outgoingInventoryTransaction;
    }

    @Override
    public double getOutgoing() {
        return outgoing;
    }

    @Override
    public void setOutgoing(double outgoing) {
        this.outgoing = outgoing;
    }

    @Override
    public String getOutgoingCurrency() {
        return outgoingCurrency;
    }

    @Override
    public void setOutgoingCurrency(String outgoingCurrency) {
        this.outgoingCurrency = outgoingCurrency;
    }
}