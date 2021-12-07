package org.btax.core;

import java.util.Date;

public interface TaxableYear {
    int getYear();
    Bookkeeping getBookkeeping();
    Date getStartLimit();
    Date getEndLimit();
    double getTaxableGain();
}