package org.btax.core.data.transactions;

import org.btax.core.data.transactions.incoming.*;
import org.btax.core.data.transactions.outgoing.*;
import org.btax.core.data.CointrackingCSVRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TransactionFactory {

    public static Transaction create(CointrackingCSVRow row) throws ParseException {
        if (isTrade(row)) return Trade.createSubtype(row);
        else if (isBlockchain(row)) return new Blockchain(row);
        else if (isGiftTip(row)) return new GiftTip(row);
        else if (isIncome(row)) return new Income(row);
        else if (isRewardBonus(row)) return new RewardBonus(row);
        else if (isDonation(row)) return new Donation(row);
        else if (isFee(row)) return new Fee(row);
        else if (isGift(row)) return new Gift(row);
        else if (isLost(row)) return new Lost(row);
        else if (isSpend(row)) return new Spend(row);
        else if (isStolenHackedFraud(row)) return new StolenHackedFraud(row);
        else if (isDeposit(row)) return new Deposit(row);
        else if (isWithdrawal(row)) return new Withdrawal(row);
        else throw new ParseException("csv row format is not recognized", 1);
    }

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static boolean isDeposit(CointrackingCSVRow row) {
        return row.getType().replaceAll("\\s", "").equalsIgnoreCase("DEPOSIT");
    }

    public static boolean isWithdrawal(CointrackingCSVRow row) {
        return row.getType().replaceAll("\\s", "").equalsIgnoreCase("WITHDRAWAL");
    }

    public static boolean isTrade(CointrackingCSVRow row) {
        return row.getType().replaceAll("\\s", "").equalsIgnoreCase("TRADE");
    }

    public static boolean isBlockchain(CointrackingCSVRow row) {
        int cond0 = row.getType().replaceAll("\\s", "").equalsIgnoreCase("MINING") ? 1 : 0;
        int cond1 = row.getType().replaceAll("\\s", "").equalsIgnoreCase("MINING(COMMERCIAL)") ? 1 : 0;
        int cond2 = row.getType().replaceAll("\\s", "").equalsIgnoreCase("AIRDROP") ? 1 : 0;
        int cond3 = row.getType().replaceAll("\\s", "").equalsIgnoreCase("STAKING") ? 1 : 0;
        int cond4 = row.getType().replaceAll("\\s", "").equalsIgnoreCase("MASTERNODE") ? 1 : 0;
        int cond5 = row.getType().replaceAll("\\s", "").equalsIgnoreCase("MINTING") ? 1 : 0;
        return (cond0+cond1+cond2+cond3+cond4+cond5) > 0;
    }

    public static boolean isGiftTip(CointrackingCSVRow row) {
        return row.getType().replaceAll("\\s", "").equalsIgnoreCase("GIFT/TIP");
    }

    public static boolean isIncome(CointrackingCSVRow row) {
        return row.getType().replaceAll("\\s", "").equalsIgnoreCase("INCOME");
    }

    public static boolean isRewardBonus(CointrackingCSVRow row) {
        return row.getType().replaceAll("\\s", "").equalsIgnoreCase("REWARD/BONUS");
    }

    public static boolean isDonation(CointrackingCSVRow row) {
        return row.getType().replaceAll("\\s", "").equalsIgnoreCase("DONATION");
    }

    public static boolean isFee(CointrackingCSVRow row) {
        return row.getType().toUpperCase().contains("FEE");
    }

    public static boolean isGift(CointrackingCSVRow row) {
        return row.getType().replaceAll("\\s", "").equalsIgnoreCase("GIFT");
    }

    public static boolean isLost(CointrackingCSVRow row) {
        return row.getType().replaceAll("\\s", "").equalsIgnoreCase("LOST");
    }

    public static boolean isSpend(CointrackingCSVRow row) {
        return row.getType().replaceAll("\\s", "").equalsIgnoreCase("SPEND");
    }

    public static boolean isStolenHackedFraud(CointrackingCSVRow row) {
        return row.getType().replaceAll("\\s", "").equalsIgnoreCase("STOLEN");
    }

    public static boolean isNotNumeric(String element) {
        if (element.isEmpty() || element.isBlank()) {
            return true;
        }
        try {
            Double.parseDouble(element);
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
    }

    public static double makeIncoming(CointrackingCSVRow row) throws ParseException {
        if (isNotNumeric(row.getIncoming())) {
            throw new ParseException("incoming (Buy) data is missing", 1);
        }

        return Double.parseDouble(row.getIncoming());
    }

    public static String makeIncomingCurrency(CointrackingCSVRow row) throws ParseException {
        if (row.getIncomingCurrency().isBlank() || row.getIncomingCurrency().isEmpty()) {
            throw new ParseException("incoming currency (Buy Currency) data is missing", 1);
        }

        return row.getIncomingCurrency();
    }

    public static double makeOutgoing(CointrackingCSVRow row) throws ParseException {
        if (isNotNumeric(row.getOutgoing())) {
            throw new ParseException("outgoing (Sell) data is missing", 1);
        }

        return Double.parseDouble(row.getOutgoing());
    }

    public static String makeOutgoingCurrency(CointrackingCSVRow row) throws ParseException {
        if (row.getOutgoingCurrency().isBlank() || row.getOutgoingCurrency().isEmpty()) {
            throw new ParseException("outgoing currency (Sell Currency) data is missing", 1);
        }

        return row.getOutgoingCurrency();
    }

    public static double makeFee(CointrackingCSVRow row) throws ParseException {
        if (isNotNumeric(row.getFee())) {
            return 0;
        }

        return Double.parseDouble(row.getFee());
    }

    public static String makeFeeCurrency(CointrackingCSVRow row) throws ParseException {
        return row.getFeeCurrency();
    }

    public static String makeExchange(CointrackingCSVRow row) {
        return row.getExchange();
    }

    public static String makeGroup(CointrackingCSVRow row) {
        return row.getGroup();
    }

    public static String makeComment(CointrackingCSVRow row) {
        return row.getComment();
    }

    public static double makeGoodOrServicePriceInEUR(CointrackingCSVRow row) throws ParseException {
        double price = 0;

        if (row.getComment().isBlank() || row.getComment().isEmpty()) {
            throw new ParseException("goodOrServicePriceInEUR data is missing", 1);
        }

        Pattern pattern = Pattern.compile("(?i)GOODORSERVICEPRICEINEUR=\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(row.getComment());

        if (matcher.find()) {
            String group = matcher.group(0);

            pattern = Pattern.compile("\\d+(\\.\\d+)?");
            matcher = pattern.matcher(group);

            matcher.find();
            String priceAsString = matcher.group(0);
            price = Double.parseDouble(priceAsString);

        } else {
            throw new ParseException("goodOrServicePriceInEUR data is missing", 1);
        }

        return price;
    }

    public static Date makeDate(CointrackingCSVRow row) throws ParseException {
        return DATE_FORMATTER.parse(row.getDate());
    }
}