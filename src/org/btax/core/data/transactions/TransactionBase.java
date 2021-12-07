package org.btax.core.data.transactions;

import java.text.SimpleDateFormat;
import java.util.Date;

abstract public class TransactionBase implements Transaction {
    String exchange;
    String group;
    String comment;
    Date date;
    String recordString;

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TransactionBase() {}

    public TransactionBase(String exchange, String group, String comment, Date date, String recordString) {
        this.exchange = exchange;
        this.group = group;
        this.comment = comment;
        this.date = date;
        this.recordString = recordString;
    }

    @Override
    public String getExchange() {
        return exchange;
    }

    @Override
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getRecordString() {
        return recordString;
    }

    @Override
    public void setRecordString(String recordString) {
        this.recordString = recordString;
    }
}