package org.btax.core.inventory;

import org.btax.core.TransactionDate;
import org.btax.core.data.transactions.Trade;
import org.btax.core.data.transactions.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class InventoryTest {
    @Test
    void cost() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Trade(0.6, "BTC", 600.0, "EUR", 0.0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:14:00"),""));
        transactions.add(new Trade(50, "ETH", 500, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-01-01 12:15:00"),""));
        transactions.add(new Trade(1.4, "BTC", 2800, "EUR", 0, "", "Kraken", "", "", TransactionDate.parse("2017-06-01 12:14:00"),""));
        transactions.add(new Trade(4, "ETH", 0.3, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-09-01 12:15:00"),""));
        transactions.add(new Trade(10000, "EUR", 1, "BTC", 0, "", "Kraken", "", "", TransactionDate.parse("2017-12-01 12:14:00"),""));

    }
}