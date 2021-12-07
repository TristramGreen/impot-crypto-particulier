package org.btax.core;

import mock.PriceServiceMock1000;
import mock.PriceServiceMock2017;
import org.btax.core.inventory.IncomingInventoryTransaction;
import org.btax.core.inventory.Inventory;
import org.btax.core.inventory.InventoryTransaction;
import org.btax.core.inventory.OutgoingInventoryTransaction;
import org.btax.core.data.transactions.CryptoForCrypto;
import org.btax.core.data.transactions.CryptoForEUR;
import org.btax.core.data.transactions.Transaction;
import org.btax.core.data.transactions.TransactionBase;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    @Test
    void inventoryCreation() throws ParseException {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new CryptoForEUR(0.6, "BTC", 600, "EUR", 0.0, "", "Kraken", "", "", TransactionBase.DATE_FORMATTER.parse("2017-01-01 12:14:00"), ""));
        transactionList.add(new CryptoForEUR( 50, "ETH", 500, "EUR", 0, "", "Kraken", "", "", TransactionBase.DATE_FORMATTER.parse("2017-01-01 12:15:00"), ""));
        transactionList.add(new CryptoForEUR( 1.4, "BTC", 2800, "EUR", 0, "", "Kraken", "", "", TransactionBase.DATE_FORMATTER.parse("2017-06-01 12:14:00"), ""));
        transactionList.add(new CryptoForCrypto( 4, "ETH", 0.3, "BTC", 0, "", "Kraken", "", "", TransactionBase.DATE_FORMATTER.parse("2017-09-01 12:15:00"), ""));

        Inventory inventory = new Inventory("BTC", transactionList, new PriceServiceMock2017());

        assertEquals(3, inventory.getInventoryTransactions().size());
    }

    @Test
    void quantity() throws ParseException {
        PriceServiceMock2017 service = new PriceServiceMock2017();
        List<InventoryTransaction> inventoryTransactions = new ArrayList<>();
        inventoryTransactions.add(new IncomingInventoryTransaction("BTC", TransactionBase.DATE_FORMATTER.parse("2017-01-01 12:14:00"),0.6, 1));
        inventoryTransactions.add(new IncomingInventoryTransaction("BTC", TransactionBase.DATE_FORMATTER.parse("2017-06-01 12:14:00"),1.4,  1));
        inventoryTransactions.add(new OutgoingInventoryTransaction("BTC",TransactionBase.DATE_FORMATTER.parse("2017-09-01 12:15:00"), 0.3, 1));
        inventoryTransactions.add(new OutgoingInventoryTransaction("BTC", TransactionBase.DATE_FORMATTER.parse("2017-12-01 12:14:00"), 1,  1));

        Inventory inventory = new Inventory("BTC", inventoryTransactions);
        assertEquals(0.7, inventory.quantity());
    }

    @Test
    void value() throws Exception {
        PriceService service = new PriceServiceMock1000();
        List<InventoryTransaction> inventoryTransactions = new ArrayList<>();
        inventoryTransactions.add(new IncomingInventoryTransaction("BTC", TransactionBase.DATE_FORMATTER.parse("2017-01-01 12:14:00"),0.6, 1));
        inventoryTransactions.add(new IncomingInventoryTransaction("BTC", TransactionBase.DATE_FORMATTER.parse("2017-06-01 12:14:00"),1.4,  1));
        inventoryTransactions.add(new OutgoingInventoryTransaction("BTC",TransactionBase.DATE_FORMATTER.parse("2017-09-01 12:15:00"), 0.3, 1));
        inventoryTransactions.add(new OutgoingInventoryTransaction("BTC", TransactionBase.DATE_FORMATTER.parse("2017-12-01 12:14:00"), 1,  1));
        Inventory inventory = new Inventory("BTC", inventoryTransactions);
        assertEquals(700, inventory.value(new Date(), service));
    }
}