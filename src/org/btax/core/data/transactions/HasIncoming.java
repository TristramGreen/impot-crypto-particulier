package org.btax.core.data.transactions;

public interface HasIncoming extends Transaction {
    void setIncoming(double incoming);
    double getIncoming();
    void setIncomingCurrency(String incomingCurrency);
    String getIncomingCurrency();
    double acquisitionCost();
}