package org.btax.core;

import org.btax.core.inventory.Inventory;
import org.btax.core.data.transactions.*;
import org.btax.core.data.transactions.incoming.Deposit;
import org.btax.core.data.transactions.outgoing.Withdrawal;
import org.btax.core.data.CointrackingCSVRow;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Bookkeeping {
    public static final String CURRENCY = "EUR";
    private PriceService service;
    private List<Transaction> transactions = new ArrayList<>();
    private List<Inventory> inventories = new ArrayList<>();

    public static Bookkeeping createFromTransactions(List<Transaction> transactions, PriceService service) {
        Bookkeeping bookkeeping = new Bookkeeping();
        bookkeeping.setService(service);
        bookkeeping.setTransactions(transactions);
        bookkeeping.setInventories(bookkeeping.getTransactions(), service);
        return bookkeeping;
    }

    public static Bookkeeping createFromRows(List<CointrackingCSVRow> rows, PriceService service) {
        Bookkeeping bookkeeping = new Bookkeeping();
        bookkeeping.setService(service);
        bookkeeping.setTransactionsFromRows(rows);
        bookkeeping.setInventories(bookkeeping.getTransactions(), service);
        return bookkeeping;
    }

    public double value(Date date) {
        return inventories.stream()
                .map(inventory -> {
                    try {
                        return inventory.value(date, service);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        System.exit(1);
                    }
                    return 0.0;
                })
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactionsFromRows(List<CointrackingCSVRow> rows) {
        this.transactions = rows.stream()
                .map((CointrackingCSVRow row) -> {
                    Transaction tx = null;
                    try {
                        tx = TransactionFactory.create(row);
                    } catch(ParseException exception) {
                        exception.printStackTrace();
                        System.exit(1);
                    }
                    return tx;
                })
                .filter(transaction -> !(transaction instanceof Deposit))
                .filter(transaction -> !(transaction instanceof Withdrawal))
                .sorted((x, y) -> {
                    long t1 = x.getDate().getTime();
                    long t2 = y.getDate().getTime();
                    return Long.compare(t1, t2);
                })
                .collect(Collectors.toList());
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions.stream()
                .filter(transaction -> !(transaction instanceof Deposit))
                .filter(transaction -> !(transaction instanceof Withdrawal))
                .sorted((x, y) -> {
                    long t1 = x.getDate().getTime();
                    long t2 = y.getDate().getTime();
                    return Long.compare(t1, t2);
                })
                .collect(Collectors.toList());
    }

    public void setInventories(List<Transaction> transactions, PriceService service) {
        transactions.forEach(transaction -> {
            if (transaction instanceof HasIncoming) addInventory(((HasIncoming) transaction).getIncomingCurrency(), transactions, service);
            if (transaction instanceof HasOutgoing) addInventory(((HasOutgoing) transaction).getOutgoingCurrency(), transactions, service);
        });
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public Optional<Inventory> getInventory(String currency) {
        return inventories.stream().filter(inventory -> inventory.getCurrency().equalsIgnoreCase(currency)).findFirst();
    }

    public void addInventory(String currency, List<Transaction> transactions, PriceService service) {
        if (currency.equalsIgnoreCase(CURRENCY)) return;
        if (currency.isBlank() || currency.isEmpty()) return;
        if (getInventory(currency).isEmpty()) {
            inventories.add(new Inventory(currency, transactions, service));
        }
    }

    public PriceService getService() {
        return service;
    }

    public void setService(PriceService service) {
        this.service = service;
    }
}
