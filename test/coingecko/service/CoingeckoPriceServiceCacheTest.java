package coingecko.service;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoingeckoPriceServiceCacheTest {

    static String filename = "cache-tmp.json";
    static File file = new File(filename);

    @AfterAll
    public static void deleteFile() {
        file.delete();
    }

    @Test
    void cacheFileIsWritten() throws Exception {
        CoingeckoPriceServiceCache priceServiceCache = new CoingeckoPriceServiceCache(filename);
        String baseCurrency = "BTC";
        String quoteCurrency = "EUR";
        Date date = CoingeckoPriceService.COINGECKO_SNAPSHOT_DATE_FORMAT.parse("31-12-2019");
        priceServiceCache.addToCache(baseCurrency, quoteCurrency, date, 10000);
        Scanner myReader = new Scanner(file);
        assertEquals("{\"BTC-EUR-31_12_2019\":10000.0}", myReader.nextLine());
        myReader.close();
    }

    @Test
    void cacheFileIsRead() throws IOException, ParseException {
        CoingeckoPriceServiceCache priceServiceCache = new CoingeckoPriceServiceCache("test/mock/coingecko-price-service-cache-test.json");
        double price = priceServiceCache.getCache().get("BTC-EUR-31_12_2019");
        assertEquals(10000.0, price);
    }

    @Test
    void getPriceFromCacheIfPresentElseMinus1() throws Exception {
        CoingeckoPriceServiceCache priceServiceCache = new CoingeckoPriceServiceCache(filename);
        String baseCurrency = "BTC";
        String quoteCurrency = "EUR";
        Date date = CoingeckoPriceService.COINGECKO_SNAPSHOT_DATE_FORMAT.parse("31-12-2019");
        priceServiceCache.addToCache(baseCurrency, quoteCurrency, date, 10000);

        assertEquals(10000, priceServiceCache.getPriceFromCacheIfPresentElseMinus1(baseCurrency, quoteCurrency, date));
    }
}