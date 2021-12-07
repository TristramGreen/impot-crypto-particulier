package org.btax.core;

import mock.PriceServiceMockORWLTest;
import org.btax.core.data.transactions.CryptoForEUR;
import org.btax.core.data.transactions.EURForCrypto;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.btax.core.data.transactions.Transaction;

import static org.junit.jupiter.api.Assertions.*;

class TaxableYearPost2019Test {

    @Test
    void taxableGain() throws ParseException {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new CryptoForEUR( 5, "BTC", 9005, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-07-17 12:14:00"),""));
        transactionList.add(new CryptoForEUR( 1, "BTC", 16346, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-15 12:15:00"),""));
        transactionList.add(new EURForCrypto( 3744, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2019-01-09 12:14:00"),""));
        transactionList.add(new CryptoForEUR( 2.5, "BTC", 9280, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2019-03-24 12:15:00"),""));
        transactionList.add(new CryptoForEUR( 100, "GRIN", 225, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2019-03-27 12:15:00"),""));
        transactionList.add(new EURForCrypto( 224, "EUR", 50, "GRIN", 0, "", "Kraken", "", "", TransactionDate.parse("2019-06-16 12:14:00"),""));
        transactionList.add(new EURForCrypto( 10463, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2019-06-25 12:14:00"),""));

        HandoverFactory handoverFactory = new HandoverFactory();
        Bookkeeping bookkeeping = Bookkeeping.createFromTransactions(transactionList, new PriceServiceMockORWLTest());
        TaxableYearPost2019 y2019 = new TaxableYearPost2019(bookkeeping, handoverFactory, 2019);
        assertEquals(6041.2619550617255, y2019.getTaxableGain());
    }
}