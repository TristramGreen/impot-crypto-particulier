package mock;

import org.btax.core.PriceService;

import java.util.Date;

public class PriceServiceMock1000 implements PriceService {
    @Override
    public double getPrice(String baseCurrency, String quoteCurrency, Date date) {
        return 1000;
    }
}
