package org.btax.core.data.transactions.outgoing;

import org.btax.core.PriceService;
import org.btax.core.inventory.InventoryCurrencyNotFoundInTransaction;
import org.btax.core.inventory.InventoryTransaction;
import org.btax.core.inventory.OutgoingInventoryTransaction;
import org.btax.core.data.transactions.TransactionFactory;
import org.btax.core.data.transactions.HasFee;
import org.btax.core.data.transactions.HasOutgoing;
import org.btax.core.data.transactions.TransactionBase;
import org.btax.core.data.CointrackingCSVRow;

import java.text.ParseException;

public class Withdrawal extends TransactionBase implements HasOutgoing, HasFee {
    double outgoing;
    String outgoingCurrency;
    double fee;
    String feeCurrency;

    public Withdrawal(CointrackingCSVRow row) throws ParseException {
        setOutgoing(TransactionFactory.makeOutgoing(row));
        setOutgoingCurrency(TransactionFactory.makeOutgoingCurrency(row));
        setFee(TransactionFactory.makeFee(row));
        setFeeCurrency(TransactionFactory.makeFeeCurrency(row));
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
            outgoingInventoryTransaction.setQuantity(0);
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

    @Override
    public double getFee() {
        return fee;
    }

    @Override
    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String getFeeCurrency() {
        return feeCurrency;
    }

    @Override
    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

}
