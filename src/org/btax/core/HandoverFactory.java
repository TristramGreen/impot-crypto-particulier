package org.btax.core;

import coingecko.api.CurrencyNotFoundException;
import org.btax.core.data.transactions.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HandoverFactory {
    private double netCostToInitialCapitalSum = 0;

    public Handover get(Bookkeeping bookkeeping, HandoverEvent transaction) throws Exception {
        Date date = transaction.getDate();
        double netCostToInitialCapitalSum = getNetCostToInitialCapitalSum();
        double globalValue = globalValue(bookkeeping, transaction);
        double fee = fee(transaction, bookkeeping.getService());
        double price = transaction.handoverPrice();
        double globalCost = globalCost(bookkeeping, transaction);
        // TODO
        double adjustmentBenefitsBeforeHandover = 0;
        double adjustment = 0;
        //
        Handover handover = new Handover(date, netCostToInitialCapitalSum, globalValue, price, fee, adjustment, globalCost, adjustmentBenefitsBeforeHandover);
        sumNetCostToInitialCapital(handover);
        return handover;
    }

    double globalValue(Bookkeeping bookkeeping, Transaction transaction) {
        List<Transaction> transactions = bookkeeping.getTransactions().stream()
                .takeWhile(tx -> !tx.equals(transaction))
                .collect(Collectors.toList());
        return Bookkeeping.createFromTransactions(transactions, bookkeeping.getService()).value(transaction.getDate());
    }

    double globalCost(Bookkeeping bookkeeping, Transaction transaction) {
        return acquisitionCostBefore2019Regime(bookkeeping) + acquisitionCost(bookkeeping, transaction);
    }

    double acquisitionCostBefore2019Regime(Bookkeeping bookkeeping) {
        List<Transaction> transactions = bookkeeping.getTransactions().stream()
                .takeWhile(tx -> tx.getDate().before(TaxableYearPost2019.REGIME_START_DATE))
                .collect(Collectors.toList());
        return Bookkeeping.createFromTransactions(transactions, bookkeeping.getService()).getInventories().stream()
                .map(inventory -> new FIFO(inventory, TaxableYearPost2019.REGIME_START_DATE).getInventory().cost())
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    double acquisitionCost(Bookkeeping bookkeeping, Transaction transaction) {
        return bookkeeping.getTransactions().stream()
                .dropWhile(tx -> tx.getDate().before(TaxableYearPost2019.REGIME_START_DATE))
                .takeWhile(tx -> !tx.equals(transaction))
                .filter(tx -> tx instanceof HasIncoming)
                .map(tx -> (HasIncoming) tx)
                .map(HasIncoming::acquisitionCost)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    void sumNetCostToInitialCapital(Handover handover) {
        this.netCostToInitialCapitalSum += handover.netCostToInitialCapital();
    }

    double fee(Transaction transaction, PriceService service) {
        double feeConversionRate = 0;
        if (!(transaction instanceof HasFee)) {
            return 0;
        }
        HasFee transactionWithFee = (HasFee) transaction;
        if (transactionWithFee.getFee() == 0) {
            return 0;
        } else if (transactionWithFee.getFeeCurrency().equalsIgnoreCase(Bookkeeping.CURRENCY)){
            return transactionWithFee.getFee();
        } else {
            try {
                feeConversionRate = service.getPrice(transactionWithFee.getFeeCurrency(), Bookkeeping.CURRENCY, transaction.getDate());
            } catch (CurrencyNotFoundException exception) {
                System.out.println("WARNING: fee currency: "+exception.getMessage());
                return 0;
            } catch (Exception exception) {
                exception.printStackTrace();
                System.exit(1);
            }
            return transactionWithFee.getFee() * feeConversionRate;
        }
    }

    public double getNetCostToInitialCapitalSum() {
        return netCostToInitialCapitalSum;
    }
}