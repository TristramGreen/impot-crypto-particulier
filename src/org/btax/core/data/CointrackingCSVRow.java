package org.btax.core.data;

public class CointrackingCSVRow {
    private String type;
    private String incoming;
    private String incomingCurrency;
    private String outgoing;
    private String outgoingCurrency;
    private String fee;
    private String feeCurrency;
    private String exchange;
    private String group;
    private String comment;
    private String date;
    private String recordString;

    public CointrackingCSVRow(String type, String incoming, String incomingCurrency, String outgoing, String outgoingCurrency, String fee, String feeCurrency, String exchange, String group, String comment, String date, String recordString) {
        this.type = type;
        this.incoming = incoming;
        this.incomingCurrency = incomingCurrency;
        this.outgoing = outgoing;
        this.outgoingCurrency = outgoingCurrency;
        this.fee = fee;
        this.feeCurrency = feeCurrency;
        this.exchange = exchange;
        this.group = group;
        this.comment = comment;
        this.date = date;
        this.recordString = recordString;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIncoming() {
        return incoming;
    }

    public void setIncoming(String incoming) {
        this.incoming = incoming;
    }

    public String getIncomingCurrency() {
        return incomingCurrency;
    }

    public void setIncomingCurrency(String incomingCurrency) {
        this.incomingCurrency = incomingCurrency;
    }

    public String getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(String outgoing) {
        this.outgoing = outgoing;
    }

    public String getOutgoingCurrency() {
        return outgoingCurrency;
    }

    public void setOutgoingCurrency(String outgoingCurrency) {
        this.outgoingCurrency = outgoingCurrency;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFeeCurrency() {
        return feeCurrency;
    }

    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRecordString() {
        return recordString;
    }

    public void setRecordString(String recordString) {
        this.recordString = recordString;
    }
}