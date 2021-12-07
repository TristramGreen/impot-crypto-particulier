package org.btax.core;

import org.btax.core.data.transactions.HandoverEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaxableYearPost2019 extends TaxableYearBase {
    private List<Handover> handovers;
    private HandoverFactory handoverFactory;
    public static Date REGIME_START_DATE = null;

    static {
        try {
            REGIME_START_DATE = new SimpleDateFormat("yyyy").parse("2019");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public TaxableYearPost2019(Bookkeeping bookkeeping, HandoverFactory factory, int year) throws ParseException {
        super(bookkeeping, year);
        setHandoverFactory(factory);
        setHandovers(bookkeeping);
        setTaxableGain();
    }

    public void setTaxableGain() {
        this.taxableGain = getHandovers().stream()
                .map(Handover::taxableGain)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public List<Handover> getHandovers() {
        return handovers;
    }

    public void setHandovers(Bookkeeping bookkeeping) throws ParseException {
        handovers = bookkeeping.getTransactions().stream()
                .filter(transaction -> transaction instanceof HandoverEvent)
                .map(transaction -> (HandoverEvent) transaction)
                .filter(transaction -> transaction.getDate().getTime() >= getStartLimit().getTime())
                .filter(transaction -> transaction.getDate().getTime() < getEndLimit().getTime())
                .map(transaction -> {
                    Handover handover = null;
                    try {
                        handover = getHandoverFactory().get(bookkeeping, transaction);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                    return handover;
                })
                .collect(Collectors.toList());
    }

    public HandoverFactory getHandoverFactory() {
        return handoverFactory;
    }

    public void setHandoverFactory(HandoverFactory handoverFactory) {
        this.handoverFactory = handoverFactory;
    }
}