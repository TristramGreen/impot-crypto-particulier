package org.btax.core;

import org.btax.core.data.transactions.TransactionBase;

import java.text.ParseException;
import java.util.Date;

public class TransactionDate {
    public static Date parse(String dateString) {
        try {
            return TransactionBase.DATE_FORMATTER.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
