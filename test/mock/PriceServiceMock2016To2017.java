package mock;

import org.btax.core.PriceService;
import org.btax.core.TransactionDate;

import java.text.ParseException;
import java.util.Date;

public class PriceServiceMock2016To2017 implements PriceService {
    @Override
    public double getPrice(String base, String quote, Date date) {
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2016-01-01 00:00:00");
            if (date.getTime() == _date.getTime()) return 1000.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2016-06-01 00:00:00");
            if (date.getTime() == _date.getTime()) return 2000.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2017-01-01 12:14:00");
            if (date.getTime() == _date.getTime()) return 1000.0;
        }
        if (base.equalsIgnoreCase("eth") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2017-01-01 12:15:00");
            if (date.getTime() == _date.getTime()) return 10.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2017-06-01 12:14:00");
            if (date.getTime() == _date.getTime()) return 2000.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2017-09-01 12:15:00");
            if (date.getTime() == _date.getTime()) return 4000.0;
        }
        if (base.equalsIgnoreCase("eth") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2017-09-01 12:15:00");
            if (date.getTime() == _date.getTime()) return 300.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2017-12-01 12:14:00");
            if (date.getTime() == _date.getTime()) return 10000.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2018-12-01 12:14:00");
            if (date.getTime() == _date.getTime()) return 100000.0;
        }
        return 999999999; //obvious error in testing
    }
}

