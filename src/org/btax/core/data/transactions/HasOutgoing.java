package org.btax.core.data.transactions;

public interface HasOutgoing extends Transaction {
    void setOutgoing(double outgoing);
    double getOutgoing();
    void setOutgoingCurrency(String outgoingCurrency);
    String getOutgoingCurrency();
}