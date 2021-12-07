package org.btax.core.data.transactions;

import org.btax.core.Bookkeeping;
import org.btax.core.PriceService;
import org.btax.core.inventory.*;
import org.btax.core.data.CointrackingCSVRow;

import java.text.ParseException;
import java.util.Date;

public class Trade extends TransactionBase implements HasIncoming, HasOutgoing, HasFee {
    double incoming;
    String incomingCurrency;
    double outgoing;
    String outgoingCurrency;
    double fee;
    String feeCurrency;

    Trade(CointrackingCSVRow row) throws ParseException {
        setIncoming(TransactionFactory.makeIncoming(row));
        setIncomingCurrency(TransactionFactory.makeIncomingCurrency(row));
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

    public Trade(double incoming, String incomingCurrency, double outgoing, String outgoingCurrency, double fee, String feeCurrency, String exchange, String group, String comment, Date date, String recordString) {
        super(exchange, group, comment, date, recordString);
        this.incoming = incoming;
        this.incomingCurrency = incomingCurrency;
        this.outgoing = outgoing;
        this.outgoingCurrency = outgoingCurrency;
        this.fee = fee;
        this.feeCurrency = feeCurrency;
    }

    public static Trade createSubtype(CointrackingCSVRow row) throws ParseException {
        if (row.getIncomingCurrency().equalsIgnoreCase(Bookkeeping.CURRENCY)) return new EURForCrypto(row);
        else if (row.getOutgoingCurrency().equalsIgnoreCase(Bookkeeping.CURRENCY)) return new CryptoForEUR(row);
        else return new CryptoForCrypto(row);
    }

    @Override
    public InventoryTransaction createInventoryTransaction(String currency, PriceService service) throws InventoryCurrencyNotFoundInTransaction {
        return InventoryTransactionFactory.makeInventoryTransactionOfTrade(currency, this, service);
    }

    @Override
    public double acquisitionCost(){
        return 0;
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