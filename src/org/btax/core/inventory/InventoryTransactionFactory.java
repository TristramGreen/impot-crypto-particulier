package org.btax.core.inventory;

import org.btax.core.Bookkeeping;
import org.btax.core.PriceService;
import org.btax.core.data.transactions.*;

import java.util.Date;

public class InventoryTransactionFactory {

    public static InventoryTransaction makeInventoryTransactionOfTrade(String currency, Trade trade, PriceService service) throws InventoryCurrencyNotFoundInTransaction {
        InventoryTransaction inventoryTransaction = null;

        if (currency.equalsIgnoreCase(trade.getIncomingCurrency())) {
            inventoryTransaction = new IncomingInventoryTransaction();
            inventoryTransaction.setCurrency(currency);
            inventoryTransaction.setPrice(InventoryTransactionFactory.makePriceOfOutgoing(trade, service));
            inventoryTransaction.setQuantity(trade.getIncoming());
            inventoryTransaction.setDate(trade.getDate());
        } else if (currency.equalsIgnoreCase(trade.getOutgoingCurrency())) {
            inventoryTransaction = new OutgoingInventoryTransaction();
            inventoryTransaction.setCurrency(currency);
            inventoryTransaction.setPrice(InventoryTransactionFactory.makePriceOfIncoming(trade, service));
            inventoryTransaction.setQuantity(trade.getOutgoing());
            inventoryTransaction.setDate(trade.getDate());
        } else {
            throw new InventoryCurrencyNotFoundInTransaction(currency);
        }

        return inventoryTransaction;
    }

    public static double makePriceOfOutgoing(HasOutgoing outgoing, PriceService service) {
        double quote = 0;
        if (outgoing.getOutgoingCurrency().equalsIgnoreCase(Bookkeeping.CURRENCY)) {
            return outgoing.getOutgoing();
        } else {
            Date date = outgoing.getDate();
            try {
                quote = service.getPrice(outgoing.getOutgoingCurrency(), Bookkeeping.CURRENCY, date);
            } catch(Exception exception) {
                exception.printStackTrace();
                System.exit(1);
            }
            return outgoing.getOutgoing() * quote;
        }
    }

    public static double makePriceOfIncoming(HasIncoming incoming, PriceService service) {
        double quote = 0;
        if (incoming.getIncomingCurrency().equalsIgnoreCase(Bookkeeping.CURRENCY)) {
            return incoming.getIncoming();
        } else {
            Date date = incoming.getDate();
            try {
                quote = service.getPrice(incoming.getIncomingCurrency(), Bookkeeping.CURRENCY, date);
            } catch(Exception exception) {
                exception.printStackTrace();
                System.exit(1);
            }
            return incoming.getIncoming() * quote;
        }
    }

}