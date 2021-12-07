package org.btax.core;

import mock.PriceServiceMockORWLTest;
import org.btax.core.data.transactions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandoverFactoryOWRLTest {
    //https://bitcoin.fr/une-simulation-de-declaration-de-plus-values-issues-de-la-vente-dactifs-numeriques/
    HandoverFactory factoryORWLTest = new HandoverFactory();
    Bookkeeping accountingORWLTest;

    HandoverFactoryOWRLTest() {
        List<Transaction> transactionList = new ArrayList<>();

        transactionList.add(new CryptoForEUR( 5, "BTC", 9005, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-07-17 12:14:00"), ""));
        transactionList.add(new CryptoForEUR( 1, "BTC", 16346, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-15 12:15:00"), ""));
        transactionList.add(new EURForCrypto( 3744, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2019-01-09 12:14:00"), ""));
        transactionList.add(new CryptoForEUR( 2.5, "BTC", 9280, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2019-03-24 12:15:00"), ""));
        transactionList.add(new CryptoForEUR( 100, "GRIN", 225, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2019-03-27 12:15:00"), ""));
        transactionList.add(new EURForCrypto( 224, "EUR", 50, "GRIN", 0, "", "Kraken", "", "", TransactionDate.parse("2019-06-16 12:14:00"), ""));
        transactionList.add(new EURForCrypto( 10463, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2019-06-25 12:14:00"), ""));

        accountingORWLTest = Bookkeeping.createFromTransactions(transactionList, new PriceServiceMockORWLTest());
    }

    @Test
    void handovers() throws Exception {
        Handover handover1 = factoryORWLTest.get(accountingORWLTest, (HandoverEvent) accountingORWLTest.getTransactions().get(2));
        /* 212 */ assertEquals(22464, handover1.getGlobalValue());
        /* 213 */ assertEquals(3744, handover1.getPrice());
        /* 214 */ assertEquals(0, handover1.getFee());
        /* 215 */ assertEquals(3744, handover1.feeFreePrice());
        /* 216 */ assertEquals(0, handover1.getAdjustment());
        /* 217 */ assertEquals(3744, handover1.adjustmentFreePrice());
        /* 218 */ assertEquals(3744, handover1.adjustmentAndFeeFreePrice());
        /* 220 */ assertEquals(25351, handover1.getGlobalCost());
        /* 221 */ assertEquals(0, handover1.getNetCostToInitialCapitalSum());
        /* 222 */ assertEquals(0, handover1.getAdjustmentBenefitsBeforeHandover());
        /* 223 */ assertEquals(25351, handover1.netCost());
        /* taxableGain */ assertEquals(-481.16666666666697, handover1.taxableGain());
        assertEquals(4225.166666666667, handover1.netCostToInitialCapital());
        assertEquals(4225.166666666667, factoryORWLTest.getNetCostToInitialCapitalSum());

        Handover handover2 = factoryORWLTest.get(accountingORWLTest, (HandoverEvent) accountingORWLTest.getTransactions().get(5));
        /* 212 */ assertEquals(63103, handover2.getGlobalValue());
        /* 213 */ assertEquals(224, handover2.getPrice());
        /* 214 */ assertEquals(0, handover2.getFee());
        /* 215 */ assertEquals(224, handover2.feeFreePrice());
        /* 216 */ assertEquals(0, handover2.getAdjustment());
        /* 217 */ assertEquals(224, handover2.adjustmentFreePrice());
        /* 218 */ assertEquals(224, handover2.adjustmentAndFeeFreePrice());
        /* 220 */ assertEquals(34856, handover2.getGlobalCost());
        /* 221 */ assertEquals(4225.166666666667, handover2.getNetCostToInitialCapitalSum());
        /* 222 */ assertEquals(0, handover2.getAdjustmentBenefitsBeforeHandover());
        /* 223 */ assertEquals(30630.833333333332, handover2.netCost());
        /* taxableGain */ assertEquals(115.26813833468034, handover2.taxableGain());

        Handover handover3 = factoryORWLTest.get(accountingORWLTest, (HandoverEvent) accountingORWLTest.getTransactions().get(6));
        /* 212 */ assertEquals(78739, handover3.getGlobalValue());
        /* 213 */ assertEquals(10463, handover3.getPrice());
        /* 214 */ assertEquals(0, handover3.getFee());
        /* 215 */ assertEquals(10463, handover3.feeFreePrice());
        /* 216 */ assertEquals(0, handover3.getAdjustment());
        /* 217 */ assertEquals(10463, handover3.adjustmentFreePrice());
        /* 218 */ assertEquals(10463, handover3.adjustmentAndFeeFreePrice());
        /* 220 */ assertEquals(34856, handover3.getGlobalCost());
        /* 221 */ assertEquals(4333.898528331986, handover3.getNetCostToInitialCapitalSum());
        /* 222 */ assertEquals(0, handover3.getAdjustmentBenefitsBeforeHandover());
        /* 223 */ assertEquals(30522.101471668015, handover3.netCost());
        /* taxableGain */ assertEquals(6407.160483393713, handover3.taxableGain());

        System.out.println(handover1.taxableGain()+handover2.taxableGain()+handover3.taxableGain());
    }

}