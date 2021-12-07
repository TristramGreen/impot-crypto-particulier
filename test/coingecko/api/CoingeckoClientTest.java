package coingecko.api;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoingeckoClientTest {

    @Test
    void getParamsString() throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        params.put("date", "30-12-2020");
        params.put("localization", "fr");
        String query = CoingeckoClient.getParamsString(params);
        assertEquals("?date=30-12-2020&localization=fr", query);
    }

    @Test
    void getParamsStringReturnsEmptyStringWhenNoParams() throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        String query = CoingeckoClient.getParamsString(params);
        assertEquals("", query);
    }

    @Test
    void getCoinsHistoryByIdOnlyCurrentPrice() throws IOException, InterruptedException, MarketDataNotFound {
        // see https://api.coingecko.com/api/v3/coins/bitcoin/history?date=30-12-2017
        CoingeckoClient api = new CoingeckoClient();
        CoinsHistoryByIdOnlyCurrentPrice history = api.getCoinsHistoryByIdOnlyCurrentPrice("bitcoin", "30-12-2017");
        assertEquals(11345.8976447824, history.currentPrice.get("eur"));
    }

    @Test
    void getCoinsList() throws IOException, InterruptedException {
        // see https://api.coingecko.com/api/v3/coins/list
        CoingeckoClient api = new CoingeckoClient();
        List<CoinsList> list = api.getCoinsList();
        assertEquals("zoc", list.get(0).symbol);
    }
}