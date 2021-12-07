package org.btax.core.data.transactions;

public interface HasFee {
    void setFee(double fee);
    double getFee();
    void setFeeCurrency(String feeCurrency);
    String getFeeCurrency();
}