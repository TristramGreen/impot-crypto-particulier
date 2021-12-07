package org.btax.core.data.transactions;

import org.btax.core.data.transactions.incoming.*;
import org.btax.core.data.transactions.outgoing.*;
import org.btax.core.data.CointrackingCSV;
import org.btax.core.data.CointrackingCSVRow;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFactoryTest {


    String data = """
            "Withdrawal","","","10.00000000","BTC","","","Kraken","","","2021-01-25 00:00:00"
            "Deposit","10.00000000","BTC","","","","","PaperWallet1","","","2021-01-25 00:00:00"
            "Lost","","","20.00000000","ETH","","","","","","2021-01-01 01:00:00"
            "Stolen","","","10.00000000","BTC","","","","","","2020-01-20 00:00:00"
            "Donation","","","1.00000000","ETH","0.00010000","ETH","","","","2019-03-20 00:00:00"
            "Gift","","","1.00000000","ETH","","","","","","2019-03-20 00:00:00"
            "Spend","","","1.00000000","BTC","0.00010000","BTC","","","achat d'une casquette GOODORSERVICEPRICEINEUR=500.00","2019-02-01 00:00:00"
            "Mining","0.10000000","BTC","","","0.00010000","BTC","nicehash","gpuMining","wtf","2019-01-21 00:00:00"
            "Mining (commercial)","0.10000000","BTC","","","0.00010000","BTC","","","","2019-01-21 00:00:00"
            "Airdrop","100.00000000","EOS","","","","","","","","2019-01-21 00:00:00"
            "Staking","100.00000000","XTZ","","","","","","","","2019-01-21 00:00:00"
            "Masternode","100.00000000","DASH","","","","","","","","2019-01-21 00:00:00"
            "Minting","100.00000000","BURST","","","","","","","","2019-01-21 00:00:00"
            "Reward / Bonus","1.00000000","ETH","","","","","","","","2019-01-20 14:53:03"
            "Gift/Tip","1.00000000","ETH","","","","","","","guy at the bar","2019-01-20 01:00:00"
            "Income","1.00000000","BTC","","","","","","salary","goodorservicepriceineur=1400","2019-01-20 00:00:00"
            "Trade","200.00000000","EUR","10.00000000","BTC","1.00000000","EUR","Kraken","","","2017-01-02 00:00:00"
            "Trade","200.00000000","ETH","200.00000000","EUR","1.00000000","EUR","Kraken","","","2017-01-01 02:00:00"
            "Deposit","100.00000000","BTC","","","","","Coinbase","","","2017-01-01 01:50:01"
            "Other Fee","","","0.0010000","BTC","","","Bitfinex","","","2017-01-01 01:10:00"
            "Withdrawal","","","100.00000000","BTC","0.00010000","BTC","Kraken","","","2017-01-01 01:10:00"
            "Trade","100.00000000","BTC","1000.00000000","EUR","1.00000000","EUR","Kraken","placeholder","Wow quelle affaire!","2017-01-01 01:00:00"
            "Deposit","1200.00000000","EUR","","","","","Kraken","","","2017-01-01 00:50:00"
            """;

    @Test
    void createTrade() throws IOException, ParseException {
        CointrackingCSV csv = new CointrackingCSV(data);
        CointrackingCSVRow row = csv.getRows().get(21);

        Trade trade = (Trade) TransactionFactory.create(row);

        Date date = TransactionFactory.DATE_FORMATTER.parse("2017-01-01 01:00:00");

        assertEquals(100, trade.getIncoming());
        assertEquals("BTC", trade.getIncomingCurrency());
        assertEquals(1000, trade.getOutgoing());
        assertEquals("EUR", trade.getOutgoingCurrency());
        assertEquals(1, trade.getFee());
        assertEquals("EUR", trade.getFeeCurrency());
        assertEquals("Kraken", trade.getExchange());
        assertEquals("placeholder", trade.getGroup());
        assertEquals("Wow quelle affaire!", trade.getComment());
        assertEquals(date, trade.getDate());
    }

    @Test
    void createBlockchain() throws IOException, ParseException {
        CointrackingCSV csv = new CointrackingCSV(data);
        CointrackingCSVRow row = csv.getRows().get(7);

        Blockchain blockchain = (Blockchain) TransactionFactory.create(row);

        Date date = TransactionFactory.DATE_FORMATTER.parse("2019-01-21 00:00:00");

        assertEquals(0.1, blockchain.getIncoming());
        assertEquals("BTC", blockchain.getIncomingCurrency());
        assertEquals(0.0001, blockchain.getFee());
        assertEquals("BTC", blockchain.getFeeCurrency());
        assertEquals("nicehash", blockchain.getExchange());
        assertEquals("gpuMining", blockchain.getGroup());
        assertEquals("wtf", blockchain.getComment());
        assertEquals(date, blockchain.getDate());
    }

    @Test
    void createGiftTip() throws IOException, ParseException {
        CointrackingCSV csv = new CointrackingCSV(data);
        CointrackingCSVRow row = csv.getRows().get(14);

        GiftTip giftTip = (GiftTip) TransactionFactory.create(row);

        Date date = TransactionFactory.DATE_FORMATTER.parse("2019-01-20 01:00:00");

        assertEquals(1, giftTip.getIncoming());
        assertEquals("ETH", giftTip.getIncomingCurrency());
        assertEquals(0, giftTip.getFee());
        assertEquals("", giftTip.getFeeCurrency());
        assertEquals("", giftTip.getExchange());
        assertEquals("", giftTip.getGroup());
        assertEquals("guy at the bar", giftTip.getComment());
        assertEquals(date, giftTip.getDate());
    }

    @Test
    void createIncome() throws IOException, ParseException {
        CointrackingCSV csv = new CointrackingCSV(data);
        CointrackingCSVRow row = csv.getRows().get(15);

        Income income = (Income) TransactionFactory.create(row);

        Date date = TransactionFactory.DATE_FORMATTER.parse("2019-01-20 00:00:00");

        assertEquals(1, income.getIncoming());
        assertEquals("BTC", income.getIncomingCurrency());
        assertEquals(0, income.getFee());
        assertEquals("", income.getFeeCurrency());
        assertEquals("", income.getExchange());
        assertEquals("salary", income.getGroup());
        assertEquals("goodorservicepriceineur=1400", income.getComment());
        assertEquals(1400, income.getGoodOrServicePriceInEUR());
        assertEquals(date, income.getDate());
    }

    @Test
    void createRewardBonus() throws IOException, ParseException {
        CointrackingCSV csv = new CointrackingCSV(data);
        CointrackingCSVRow row = csv.getRows().get(13);

        RewardBonus rewardBonus = (RewardBonus) TransactionFactory.create(row);

        Date date = TransactionFactory.DATE_FORMATTER.parse("2019-01-20 14:53:03");

        assertEquals(1, rewardBonus.getIncoming());
        assertEquals("ETH", rewardBonus.getIncomingCurrency());
        assertEquals(0, rewardBonus.getFee());
        assertEquals("", rewardBonus.getFeeCurrency());
        assertEquals("", rewardBonus.getExchange());
        assertEquals("", rewardBonus.getGroup());
        assertEquals("", rewardBonus.getComment());
        assertEquals(date, rewardBonus.getDate());
    }

    @Test
    void createDonation() throws IOException, ParseException {
        CointrackingCSV csv = new CointrackingCSV(data);
        CointrackingCSVRow row = csv.getRows().get(4);

        Donation donation = (Donation) TransactionFactory.create(row);

        Date date = TransactionFactory.DATE_FORMATTER.parse("2019-03-20 00:00:00");

        assertEquals(1, donation.getOutgoing());
        assertEquals("ETH", donation.getOutgoingCurrency());
        assertEquals(0.0001, donation.getFee());
        assertEquals("ETH", donation.getFeeCurrency());
        assertEquals("", donation.getExchange());
        assertEquals("", donation.getGroup());
        assertEquals("", donation.getComment());
        assertEquals(date, donation.getDate());
    }

    @Test
    void createFee() throws IOException, ParseException {
        CointrackingCSV csv = new CointrackingCSV(data);
        CointrackingCSVRow row = csv.getRows().get(19);

        Fee fee = (Fee) TransactionFactory.create(row);

        Date date = TransactionFactory.DATE_FORMATTER.parse("2017-01-01 01:10:00");

        assertEquals(0.001, fee.getOutgoing());
        assertEquals("BTC", fee.getOutgoingCurrency());
        assertEquals("Bitfinex", fee.getExchange());
        assertEquals("", fee.getGroup());
        assertEquals("", fee.getComment());
        assertEquals(date, fee.getDate());
    }

    @Test
    void createGift() throws IOException, ParseException {
        CointrackingCSV csv = new CointrackingCSV(data);
        CointrackingCSVRow row = csv.getRows().get(5);

        Gift gift = (Gift) TransactionFactory.create(row);

        Date date = TransactionFactory.DATE_FORMATTER.parse("2019-03-20 00:00:00");

        assertEquals(1, gift.getOutgoing());
        assertEquals("ETH", gift.getOutgoingCurrency());
        assertEquals(0, gift.getFee());
        assertEquals("", gift.getFeeCurrency());
        assertEquals("", gift.getExchange());
        assertEquals("", gift.getGroup());
        assertEquals("", gift.getComment());
        assertEquals(date, gift.getDate());
    }

    @Test
    void createSpend() throws IOException, ParseException {
        CointrackingCSV csv = new CointrackingCSV(data);
        CointrackingCSVRow row = csv.getRows().get(6);

        Spend spend = (Spend) TransactionFactory.create(row);

        Date date = TransactionFactory.DATE_FORMATTER.parse("2019-02-01 00:00:00");

        assertEquals(1, spend.getOutgoing());
        assertEquals("BTC", spend.getOutgoingCurrency());
        assertEquals(0.0001, spend.getFee());
        assertEquals("BTC", spend.getFeeCurrency());
        assertEquals("", spend.getExchange());
        assertEquals("", spend.getGroup());
        assertEquals("achat d'une casquette GOODORSERVICEPRICEINEUR=500.00", spend.getComment());
        assertEquals(500, spend.getGoodOrServicePriceInEUR());
        assertEquals(date, spend.getDate());
    }

    @Test
    void createStolenHackedFraud() throws IOException, ParseException {
        CointrackingCSV csv = new CointrackingCSV(data);
        CointrackingCSVRow row = csv.getRows().get(3);

        StolenHackedFraud stolen = (StolenHackedFraud) TransactionFactory.create(row);

        Date date = TransactionFactory.DATE_FORMATTER.parse("2020-01-20 00:00:00");

        assertEquals(10, stolen.getOutgoing());
        assertEquals("BTC", stolen.getOutgoingCurrency());
        assertEquals("", stolen.getExchange());
        assertEquals("", stolen.getGroup());
        assertEquals("", stolen.getComment());
        assertEquals(date, stolen.getDate());
    }

    @Test
    void makeGoodOrServicePriceInEUR() throws ParseException {
        CointrackingCSVRow row0 = new CointrackingCSVRow("", "", "", "", "", "", "", "", "", "goodOrServicePriceInEUR=500", "", "");
        CointrackingCSVRow row1 = new CointrackingCSVRow("", "", "", "", "", "", "", "", "", "GOODorservicepriceineur=500", "", "");
        CointrackingCSVRow row2 = new CointrackingCSVRow("", "", "", "", "", "", "", "", "", "l'individu ne doit pas etre trop matrixé GOODorservicepriceineur=5955.51251 we are all satoshi", "", "");

        assertEquals(500, TransactionFactory.makeGoodOrServicePriceInEUR(row0));
        assertEquals(500, TransactionFactory.makeGoodOrServicePriceInEUR(row1));
        assertEquals(5955.51251, TransactionFactory.makeGoodOrServicePriceInEUR(row2));
    }

}