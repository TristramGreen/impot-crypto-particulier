package org.btax.core;

import org.btax.core.data.transactions.HasOutgoing;
import org.btax.core.data.transactions.Transaction;
import org.btax.core.inventory.Inventory;

import java.util.List;
import java.util.stream.Collectors;

public class ConsistencyCheck {

    public ConsistencyCheck(Bookkeeping bookkeeping) throws InventoryOverflowException {
        checkThereIsNoOverflow(bookkeeping);
    }

    public void checkThereIsNoOverflow(Bookkeeping bookkeeping) throws InventoryOverflowException {
        List<HasOutgoing> transactionsWithOutgoing = bookkeeping.getTransactions().stream()
                .filter(transaction -> transaction instanceof HasOutgoing)
                .map(transaction -> (HasOutgoing) transaction)
                .filter(transaction -> !transaction.getOutgoingCurrency().equalsIgnoreCase(Bookkeeping.CURRENCY))
                .collect(Collectors.toList());

        for (HasOutgoing tx : transactionsWithOutgoing) {
            List<Transaction> transactionsHappeningBeforeTheTransaction = bookkeeping.getTransactions().stream()
                    .takeWhile(transaction1 -> !transaction1.equals(tx))
                    .collect(Collectors.toList());
            Inventory inventory = new Inventory(tx.getOutgoingCurrency(), transactionsHappeningBeforeTheTransaction, bookkeeping.getService());


            if ( (inventory.quantity() - tx.getOutgoing()) < 0 ) throw new InventoryOverflowException(tx, inventory);
        }
    }
}
