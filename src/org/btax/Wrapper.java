package org.btax;

import org.btax.core.*;
import org.btax.core.data.transactions.HasOutgoing;
import org.btax.core.data.transactions.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Wrapper {
    private Map<Integer, TaxableYear> taxableYears = new HashMap<>();
    private HandoverFactory handoverFactory = new HandoverFactory();
    private Bookkeeping bookkeeping;

    Wrapper(Bookkeeping bookkeeping) {
        setTaxableYears(bookkeeping);
        setBookkeeping(bookkeeping);
    }

    public Map<Integer, TaxableYear> getTaxableYears() {
        return taxableYears;
    }

    public void setTaxableYears(Bookkeeping bookkeeping) {
       bookkeeping.getTransactions().stream()
                .filter(transaction -> transaction instanceof HasOutgoing)
                .map(Transaction::getDate)
                .map(date -> Integer.parseInt(new SimpleDateFormat("yyyy").format(date)))
                .distinct()
                .forEach(year -> {
                    TaxableYear taxableYear = null;
                    try {
                        taxableYear = year >= 2019 ? new TaxableYearPost2019(bookkeeping, handoverFactory, year)
                                : new TaxableYearPre2019(bookkeeping, year);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                    getTaxableYears().put(year, taxableYear);
                });
    }

    public void displayAll() {
        getTaxableYears().forEach((year, taxableYear) -> {
            if (taxableYear instanceof TaxableYearPost2019) View.displayTaxableYearPost2019((TaxableYearPost2019) taxableYear);
            if (taxableYear instanceof TaxableYearPre2019) View.displayTaxableYearPre2019((TaxableYearPre2019) taxableYear);
            System.out.println();
        });
        View.displayInventories(getBookkeeping().getInventories());
    }

    public Bookkeeping getBookkeeping() {
        return bookkeeping;
    }

    public void setBookkeeping(Bookkeeping bookkeeping) {
        this.bookkeeping = bookkeeping;
    }
}
