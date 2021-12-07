package org.btax.core.data.transactions.incoming;

import org.btax.core.PriceService;
import org.btax.core.inventory.IncomingInventoryTransaction;
import org.btax.core.inventory.InventoryCurrencyNotFoundInTransaction;
import org.btax.core.inventory.InventoryTransaction;
import org.btax.core.data.transactions.HasFee;
import org.btax.core.data.transactions.HasIncoming;
import org.btax.core.data.transactions.TransactionBase;
import org.btax.core.data.transactions.TransactionFactory;
import org.btax.core.data.CointrackingCSVRow;

import java.text.ParseException;

public class RewardBonus extends TransactionBase implements HasIncoming, HasFee {
    double incoming;
    String incomingCurrency;
    double fee;
    String feeCurrency;

    public RewardBonus(CointrackingCSVRow row) throws ParseException {
        setIncoming(TransactionFactory.makeIncoming(row));
        setIncomingCurrency(TransactionFactory.makeIncomingCurrency(row));
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
        IncomingInventoryTransaction outgoingInventoryTransaction = new IncomingInventoryTransaction();
        if (currency.equalsIgnoreCase(getIncomingCurrency())) {
            outgoingInventoryTransaction.setCurrency(currency);
            outgoingInventoryTransaction.setPrice(0);
            outgoingInventoryTransaction.setQuantity(getIncoming());
            outgoingInventoryTransaction.setDate(getDate());
        } else throw new InventoryCurrencyNotFoundInTransaction(currency);
        return outgoingInventoryTransaction;
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