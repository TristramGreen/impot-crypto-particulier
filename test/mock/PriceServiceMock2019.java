package mock;

import org.btax.core.PriceService;
import org.btax.core.TransactionDate;

import java.util.Date;

public class PriceServiceMock2019 implements PriceService {
    @Override
    public double getPrice(String base, String quote, Date date) {
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-01-01 12:14:00");
            if (date.getTime() == _date.getTime()) return 1000.0;
        }
        if (base.equalsIgnoreCase("eth") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-01-01 12:15:00");
            if (date.getTime() == _date.getTime()) return 10.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-06-01 12:14:00");
            if (date.getTime() == _date.getTime()) return 2000.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-09-01 12:15:00");
            if (date.getTime() == _date.getTime()) return 4000.0;
        }
        if (base.equalsIgnoreCase("eth") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-09-01 12:15:00");
            if (date.getTime() == _date.getTime()) return 300.0;
        }
        if (base.equalsIgnoreCase("eth") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-12-01 12:14:00");
            if (date.getTime() == _date.getTime()) return 500.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-12-01 12:14:00");
            if (date.getTime() == _date.getTime()) return 10000.0;
        }
        return 999999999; //obvious error in testing
    }
}
