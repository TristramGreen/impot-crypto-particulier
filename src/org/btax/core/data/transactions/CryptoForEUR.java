package org.btax.core.data.transactions;

import org.btax.core.data.CointrackingCSVRow;

import java.text.ParseException;
import java.util.Date;

public class CryptoForEUR extends Trade {
    public CryptoForEUR(CointrackingCSVRow row) throws ParseException {
        super(row);
    }

    public CryptoForEUR(double incoming, String incomingCurrency, double outgoing, String outgoingCurrency, double fee, String feeCurrency, String exchange, String group, String comment, Date date, String recordString) {
        super(incoming, incomingCurrency, outgoing, outgoingCurrency, fee, feeCurrency, exchange, group, comment, date, recordString);
    }

    @Override
    public double acquisitionCost(){
        return getOutgoing();
    }
}