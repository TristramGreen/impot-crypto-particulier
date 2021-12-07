package org.btax.core;
import mock.PriceServiceMock2016To2017;
import mock.PriceServiceMock2017;
import org.junit.jupiter.api.Test;

import org.btax.core.data.transactions.Transaction;
import  org.btax.core.data.transactions.Trade;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

class TaxableYearPre2019Test {

    @Test
    void getTaxableGains2017() throws ParseException {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Trade( 0.6, "BTC", 600, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"), ""));
        transactionList.add(new Trade( 50, "ETH", 500, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:15:00"), ""));
        transactionList.add(new Trade( 1.4, "BTC", 2800, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-06-01 12:14:00"), ""));
        transactionList.add(new Trade( 4, "ETH", 0.3, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-09-01 12:15:00"), ""));
        transactionList.add(new Trade( 10000, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-01 12:14:00"), ""));
        transactionList.add(new Trade( 10000, "EUR", 0.1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2018-12-01 12:14:00"), ""));

        Bookkeeping accounting = Bookkeeping.createFromTransactions(transactionList, new PriceServiceMock2017());
        TaxableYearPre2019 y2017 = new TaxableYearPre2019(accounting, 2017);
        assertEquals(8300, y2017.getTaxableGain());
    }

    @Test
    void getTaxableGains2017WithPriorTransactions() throws ParseException {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Trade( 10, "BTC", 10000, "EUR", 0.0, "", "", "", "", TransactionDate.parse("2016-01-01 00:00:00"), ""));
        transactionList.add(new Trade( 20000, "EUR", 10, "BTC", 0.0, "", "", "", "", TransactionDate.parse("2016-06-01 00:00:00"), ""));
        transactionList.add(new Trade( 0.6, "BTC", 600, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"), ""));
        transactionList.add(new Trade( 50, "ETH", 500, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:15:00"), ""));
        transactionList.add(new Trade( 1.4, "BTC", 2800, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-06-01 12:14:00"), ""));
        transactionList.add(new Trade( 4, "ETH", 0.3, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-09-01 12:15:00"), ""));
        transactionList.add(new Trade( 10000, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-01 12:14:00"), ""));
        transactionList.add(new Trade( 10000, "EUR", 0.1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2018-12-01 12:14:00"), ""));

        Bookkeeping accounting = Bookkeeping.createFromTransactions(transactionList, new PriceServiceMock2016To2017());
        TaxableYearPre2019 y2017 = new TaxableYearPre2019(accounting, 2017);
        assertEquals(8300, y2017.getTaxableGain());
    }

    @Test
    void getTaxableGains2016And2017() throws ParseException {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Trade( 10, "BTC", 10000, "EUR", 0.0, "", "", "", "", TransactionDate.parse("2016-01-01 00:00:00"), ""));
        transactionList.add(new Trade( 20000, "EUR", 10, "BTC", 0.0, "", "", "", "", TransactionDate.parse("2016-06-01 00:00:00"), ""));
        transactionList.add(new Trade( 0.6, "BTC", 600, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"), ""));
        transactionList.add(new Trade( 50, "ETH", 500, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:15:00"), ""));
        transactionList.add(new Trade( 1.4, "BTC", 2800, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-06-01 12:14:00"), ""));
        transactionList.add(new Trade( 4, "ETH", 0.3, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-09-01 12:15:00"), ""));
        transactionList.add(new Trade( 10000, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-01 12:14:00"), ""));
        transactionList.add(new Trade( 10000, "EUR", 0.1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2018-12-01 12:14:00"), ""));

        Bookkeeping accounting = Bookkeeping.createFromTransactions(transactionList, new PriceServiceMock2016To2017());
        TaxableYearPre2019 y2016 = new TaxableYearPre2019(accounting, 2016);
        TaxableYearPre2019 y2017 = new TaxableYearPre2019(accounting, 2017);
        assertEquals(10000, y2016.getTaxableGain());
        assertEquals(8300, y2017.getTaxableGain());
    }

}