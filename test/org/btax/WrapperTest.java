package org.btax;

import mock.PriceServiceMock2017;
import org.btax.core.Bookkeeping;
import mock.PriceServiceMockORWLTest;
import org.btax.core.TransactionDate;
import org.btax.core.data.transactions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WrapperTest {

    @Test
    void setTaxableYears() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new CryptoForEUR( 5, "BTC", 1000, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-07-17 12:14:00"),""));
        transactionList.add(new EURForCrypto( 20000, "EUR", 5, "BTC", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-07-17 12:14:00"),""));
        transactionList.add(new CryptoForEUR( 5, "BTC", 9005, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-07-17 12:14:00"),""));
        transactionList.add(new CryptoForEUR( 1, "BTC", 16346, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-15 12:15:00"),""));
        transactionList.add(new EURForCrypto( 3744, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2019-01-09 12:14:00"),""));
        transactionList.add(new CryptoForEUR( 2.5, "BTC", 9280, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2019-03-24 12:15:00"),""));
        transactionList.add(new CryptoForEUR( 100, "GRIN", 225, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2019-03-27 12:15:00"),""));
        transactionList.add(new EURForCrypto( 224, "EUR", 50, "GRIN", 0, "", "Kraken", "", "", TransactionDate.parse("2019-06-16 12:14:00"),""));
        transactionList.add(new EURForCrypto( 10463, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2019-06-25 12:14:00"),""));

        Bookkeeping bookkeeping = Bookkeeping.createFromTransactions(transactionList, new PriceServiceMockORWLTest());

        Wrapper wrapper = new Wrapper(bookkeeping);

        double gain2017 = 19000;
        double gain2019 = 6041.2619550617255;

        assertEquals(gain2017, wrapper.getTaxableYears().get(2017).getTaxableGain());
        assertEquals(gain2019, wrapper.getTaxableYears().get(2019).getTaxableGain());
    }
}