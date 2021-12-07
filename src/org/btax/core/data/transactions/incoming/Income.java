package org.btax.core.data.transactions.incoming;

import org.btax.core.PriceService;
import org.btax.core.inventory.IncomingInventoryTransaction;
import org.btax.core.inventory.InventoryCurrencyNotFoundInTransaction;
import org.btax.core.inventory.InventoryTransaction;
import org.btax.core.data.transactions.*;
import org.btax.core.data.CointrackingCSVRow;

import java.text.ParseException;

public class Income extends TransactionBase implements HasIncoming, HasFee, HasGoodOrServicePriceInEUR {
    double incoming;
    String incomingCurrency;
    double fee;
    String feeCurrency;
    double goodOrServicePriceInEUR;

    public Income(CointrackingCSVRow row) throws ParseException {
        setIncoming(TransactionFactory.makeIncoming(row));
        setIncomingCurrency(TransactionFactory.makeIncomingCurrency(row));
        setFee(TransactionFactory.makeFee(row));
        setFeeCurrency(TransactionFactory.makeFeeCurrency(row));
        setExchange(TransactionFactory.makeExchange(row));
        setGroup(TransactionFactory.makeGroup(row));
        setComment(TransactionFactory.makeComment(row));
        setDate(TransactionFactory.makeDate(row));
        setGoodOrServicePriceInEUR(TransactionFactory.makeGoodOrServicePriceInEUR(row));
        setRecordString(row.getRecordString());
    }

    @Override
    public InventoryTransaction createInventoryTransaction(String currency, PriceService service) throws InventoryCurrencyNotFoundInTransaction {
        IncomingInventoryTransaction outgoingInventoryTransaction = new IncomingInventoryTransaction();
        if (currency.equalsIgnoreCase(getIncomingCurrency())) {
            outgoingInventoryTransaction.setCurrency(currency);
            outgoingInventoryTransaction.setPrice(getGoodOrServicePriceInEUR());
            outgoingInventoryTransaction.setQuantity(getIncoming());
            outgoingInventoryTransaction.setDate(getDate());
        } else throw new InventoryCurrencyNotFoundInTransaction(currency);
        return outgoingInventoryTransaction;
    }

    @Override
    public double acquisitionCost(){
        return getGoodOrServicePriceInEUR();
    }

    @Override
    public double getIncoming() {
        return incoming;
    }

    @Override
    public void setIncoming(double incoming) {
        this.incoming = incoming;
    }

    @Override
    public String getIncomingCurrency() {
        return incomingCurrency;
    }

    @Override
    public void setIncomingCurrency(String incomingCurrency) {
        this.incomingCurrency = incomingCurrency;
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

    @Override
    public double getGoodOrServicePriceInEUR() {
        return goodOrServicePriceInEUR;
    }

    @Override
    public void setGoodOrServicePriceInEUR(double goodOrServicePriceInEUR) {
        this.goodOrServicePriceInEUR = goodOrServicePriceInEUR;
    }
}