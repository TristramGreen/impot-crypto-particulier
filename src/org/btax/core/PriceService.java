package org.btax.core;

import java.util.Date;

public interface PriceService {
    double getPrice(String baseCurrency, String quoteCurrency, Date date) throws Exception;
}
