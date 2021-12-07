package org.btax.core;

import mock.PriceServiceMock1000;
import mock.PriceServiceMock2017;
import org.btax.core.inventory.IncomingInventoryTransaction;
import org.btax.core.data.transactions.Trade;
import org.btax.core.data.transactions.Transaction;
import org.btax.core.inventory.InventoryTransaction;
import org.btax.core.data.CointrackingCSV;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookkeepingTest {
    @Test
    public void setTransactions() throws IOException {
        String data = "Trade,515.66990000,EUR,12.19483962,ETH,0.03162432,ETH,Kraken,Kraken Ledger,,2017-04-12 21:38:13";

        CointrackingCSV csv = new CointrackingCSV(data);
        Bookkeeping bookkeeping = Bookkeeping.createFromRows(csv.getRows(), new PriceServiceMock2017());

        Trade trade = (Trade) bookkeeping.getTransactions().get(0);

        assertEquals(515.6699, trade.getIncoming());
        assertEquals(1, bookkeeping.getTransactions().size());
    }

    @Test
    public void sortTransactionHistoryByDate() throws IOException {
        String data = "Trade,1.00000000,ETH,45.00000000,EUR,1.73000000,EUR,Coinbase,,,2017-03-24 12:15:46\n"+
                "Trade,1.10547881,ETH,53.03000000,EUR,2.03000000,EUR,Coinbase,,,2017-03-27 16:26:50\n"+
                "Trade,0.99527209,ETH,44.77000000,EUR,1.72000000,EUR,Coinbase,,,2017-03-24 12:14:14\n";

        CointrackingCSV csv = new CointrackingCSV(data);
        Bookkeeping bookkeeping = Bookkeeping.createFromRows(csv.getRows(), new PriceServiceMock2017());

        assertEquals(0.99527209, ((Trade) bookkeeping.getTransactions().get(0)).getIncoming());
        assertEquals(1, ((Trade) bookkeeping.getTransactions().get(1)).getIncoming());
        assertEquals(1.10547881, ((Trade) bookkeeping.getTransactions().get(2)).getIncoming());
    }

    @Test
    void setInventories() throws ParseException {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Trade(0.6, "BTC", 600.0, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"), ""));
        transactions.add(new Trade(50, "ETH", 500, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:15:00"), ""));
        transactions.add(new Trade(1.4, "BTC", 2800, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-06-01 12:14:00"), ""));
        transactions.add(new Trade(4, "ETH", 0.3, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-09-01 12:15:00"), ""));
        transactions.add(new Trade(10000, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-01 12:14:00"), ""));

        Bookkeeping bookkeeping = Bookkeeping.createFromTransactions(transactions, new PriceServiceMock2017());

        assertEquals(2, bookkeeping.getInventories().size());
        assertTrue(bookkeeping.getInventory("btc").isPresent());
        assertTrue(bookkeeping.getInventory("eth").isPresent());

        InventoryTransaction inventoryTransaction;

        inventoryTransaction = bookkeeping.getInventory("btc").get().getInventoryTransactions().get(0);
        assertEquals(TransactionDate.parse("2017-01-01 12:14:00"), inventoryTransaction.getDate());
        assertEquals("BTC", inventoryTransaction.getCurrency());
        assertEquals(0.6, inventoryTransaction.getQuantity());
        assertEquals(600.0, inventoryTransaction.getPrice());
        assertEquals(true, inventoryTransaction instanceof IncomingInventoryTransaction);

        inventoryTransaction = bookkeeping.getInventory("btc").get().getInventoryTransactions().get(1);
        assertEquals(TransactionDate.parse("2017-06-01 12:14:00"), inventoryTransaction.getDate());
        assertEquals("BTC", inventoryTransaction.getCurrency());
        assertEquals(1.4, inventoryTransaction.getQuantity());
        assertEquals(2800, inventoryTransaction.getPrice());
        assertEquals(true, inventoryTransaction instanceof IncomingInventoryTransaction);

        inventoryTransaction = bookkeeping.getInventory("eth").get().getInventoryTransactions().get(1);
        assertEquals(TransactionDate.parse("2017-09-01 12:15:00"), inventoryTransaction.getDate());
        assertEquals("ETH", inventoryTransaction.getCurrency());
        assertEquals(4, inventoryTransaction.getQuantity());
        assertEquals(1200, inventoryTransaction.getPrice());
        assertEquals(true, inventoryTransaction instanceof IncomingInventoryTransaction);

    }

    @Test
    void value() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Trade(0.6, "BTC", 600.0, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"), ""));
        transactions.add(new Trade(50, "ETH", 500, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:15:00"), ""));
        transactions.add(new Trade(1.4, "BTC", 2800, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-06-01 12:14:00"), ""));
        transactions.add(new Trade(4, "ETH", 0.3, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-09-01 12:15:00"), ""));
        transactions.add(new Trade(10000, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-01 12:14:00"), ""));

        Bookkeeping bookkeeping = Bookkeeping.createFromTransactions(transactions, new PriceServiceMock1000());
        assertEquals(54700, bookkeeping.value(new Date()));
    }
}