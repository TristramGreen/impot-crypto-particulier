package coingecko.service;

import org.btax.core.PriceService;
import org.btax.core.TransactionDate;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CoingeckoPriceServiceTest {

    @Test
    void getPrice() throws Exception {
        String baseCurrency = "BtC", quoteCurrency = "eUR";
        Date date = TransactionDate.parse("2019-07-27 14:00:00");

        PriceService service = new CoingeckoPriceService();

        assertEquals(8841.707503476502,service.getPrice(baseCurrency, quoteCurrency, date));
        assertEquals(8841.707503476502,service.getPrice(baseCurrency, quoteCurrency, date));
    }

}