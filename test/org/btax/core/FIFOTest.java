package org.btax.core;

import mock.PriceServiceMock2017;
import org.btax.core.data.transactions.incoming.Blockchain;
import org.btax.core.inventory.Inventory;
import org.btax.core.data.transactions.Transaction;
import org.btax.core.data.transactions.Trade;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FIFOTest {

    @Test
    void getGain3() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Trade(0.7, "BTC", 700, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"), ""));
        transactionList.add(new Trade(50, "ETH", 500, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:15:00"), ""));
        transactionList.add(new Trade(1.4, "BTC", 2800, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-06-01 12:14:00"), ""));
        transactionList.add(new Trade(4, "ETH", 0.3, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-09-01 12:15:00"), ""));
        transactionList.add(new Trade(10000, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-01 12:14:00"), ""));

        Inventory inventory = new Inventory("BTC", transactionList, new PriceServiceMock2017());
        FIFO fifo = new FIFO(inventory, new Date());

        assertEquals(8400, fifo.getGain());
    }

    @Test
    void getGain2() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Trade(0.6, "BTC", 600, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"), ""));
        transactionList.add(new Trade(50, "ETH", 500, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:15:00"), ""));
        transactionList.add(new Trade(1.4, "BTC", 2800, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-06-01 12:14:00"), ""));
        transactionList.add(new Trade(4, "ETH", 0.3, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-09-01 12:15:00"), ""));
        transactionList.add(new Trade(10000, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-01 12:14:00"), ""));

        Inventory inventory = new Inventory("BTC", transactionList, new PriceServiceMock2017());
        FIFO fifo = new FIFO(inventory, new Date());

        assertEquals(8300, fifo.getGain());
    }

    @Test
    void getGain0() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Trade(1, "BTC", 1000, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"), ""));
        transactionList.add(new Trade(10000, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-01 12:14:00"), ""));

        Inventory inventory = new Inventory("BTC", transactionList, new PriceServiceMock2017());
        FIFO fifo = new FIFO(inventory, new Date());

        assertEquals(9000, fifo.getGain());
    }

    @Test
    void getGain1() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Trade(0.1, "BTC", 100, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"), ""));
        transactionList.add(new Trade(0.9, "BTC", 200, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"), ""));
        transactionList.add(new Trade(10000, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-01 12:14:00"), ""));

        Inventory inventory = new Inventory("BTC", transactionList, new PriceServiceMock2017());
        FIFO fifo = new FIFO(inventory, new Date());

        assertEquals(9700, fifo.getGain());
    }

    @Test
    void FIFODateLimit() throws ParseException {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Trade(1, "BTC", 100, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"), ""));
        transactionList.add(new Trade(1, "BTC", 100, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-02-01 12:14:00"), ""));
        transactionList.add(new Trade(1, "BTC", 100, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-03-01 12:14:00"), ""));
        transactionList.add(new Trade(1, "BTC", 100, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-04-01 12:14:00"), ""));
        transactionList.add(new Trade(300, "EUR", 3, "BTC", 0.0, "", "Kraken", "", "", TransactionDate.parse("2018-04-01 12:14:00"), ""));

        Inventory inventory = new Inventory("BTC", transactionList, new PriceServiceMock2017());
        Date limit = new SimpleDateFormat("yyyy").parse(String.valueOf(2018));
        FIFO fifo = new FIFO(inventory, limit);

        assertEquals(fifo.getInventory().quantity(), inventory.quantity());
    }

    @Test
    void WhenThereIsRemainingDust() throws ParseException {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Blockchain(0.27413539, "BTC", 0, "", "", "", "", TransactionDate.parse("2017-10-17 22:00:00"), ""));
        transactionList.add(new Trade(17.4955508, "OMG", 0.02540563, "BTC", 0.00006334, "BTC", "", "", "", TransactionDate.parse("2017-10-17 22:26:50"), ""));
        transactionList.add(new Trade(84.42011481, "OMG", 0.12308075, "BTC", 0.00030691, "BTC", "", "", "", TransactionDate.parse("2017-10-17 22:34:29"), ""));
        transactionList.add(new Trade(86.12419894, "OMG", 0.125649, "BTC", 0.00031333, "BTC", "", "", "", TransactionDate.parse("2017-10-17 22:34:59"), ""));
        transactionList.add(new Trade(0.28098465, "BTC", 188.03986455, "OMG", 0.0007042, "BTC", "", "", "", TransactionDate.parse("2017-10-18 03:31:02"), ""));

        Inventory inventory = new Inventory("OMG", transactionList, (baseCurrency, quoteCurrency, date) -> 0);
        Date limit = new SimpleDateFormat("yyyy").parse(String.valueOf(2018));
        FIFO fifo = new FIFO(inventory, limit);

    }

}