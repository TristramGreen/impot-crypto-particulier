package mock;

import org.btax.core.PriceService;
import org.btax.core.TransactionDate;

import java.util.Date;

public class PriceServiceMockORWLTest implements PriceService {
    //https://bitcoin.fr/une-simulation-de-declaration-de-plus-values-issues-de-la-vente-dactifs-numeriques/
    @Override
    public double getPrice(String base, String quote, Date date) {
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2017-07-17 12:14:00");
            if (date.getTime() == _date.getTime()) return 1801.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2017-12-15 12:15:00");
            if (date.getTime() == _date.getTime()) return 16346.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-01-09 12:14:00");
            if (date.getTime() == _date.getTime()) return 3744.0;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-03-24 12:15:00");
            if (date.getTime() == _date.getTime()) return 3712.0;
        }
        if (base.equalsIgnoreCase("grin") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-03-27 12:15:00");
            if (date.getTime() == _date.getTime()) return 2.25;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-06-16 12:14:00");
            if (date.getTime() == _date.getTime()) return 8354;
        }
        if (base.equalsIgnoreCase("grin") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-06-16 12:14:00");
            if (date.getTime() == _date.getTime()) return 4.48;
        }
        if (base.equalsIgnoreCase("btc") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-06-25 12:14:00");
            if (date.getTime() == _date.getTime()) return 10463.0;
        }
        if (base.equalsIgnoreCase("grin") && quote.equalsIgnoreCase("eur")) {
            Date _date = TransactionDate.parse("2019-06-25 12:14:00");
            if (date.getTime() == _date.getTime()) return 5.33;
        }
        return 999999999; //obvious error in testing
    }
}
