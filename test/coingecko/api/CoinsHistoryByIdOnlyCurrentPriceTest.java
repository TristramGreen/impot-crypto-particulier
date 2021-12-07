package coingecko.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinsHistoryByIdOnlyCurrentPriceTest {

    @Test
    void deserialize() throws MarketDataNotFound {
            String body = """
        {
            "id": "bitcoin",
            "symbol": "btc",
            "name": "Bitcoin",
            "market_data": {
                "current_price": {
                    "eur": 11345.8976447824,
                    "bch": 5.76928286478153
                }
            }
        }
        """;

        CoinsHistoryByIdOnlyCurrentPrice bitcoinHistory = CoinsHistoryByIdOnlyCurrentPrice.deserialize(body);
        assertEquals("bitcoin", bitcoinHistory.id);
        assertEquals("btc", bitcoinHistory.symbol);
        assertEquals("Bitcoin", bitcoinHistory.name);
        assertEquals(11345.8976447824, bitcoinHistory.currentPrice.get("eur"));
        assertEquals(5.76928286478153, bitcoinHistory.currentPrice.get("bch"));
    }

    @Test
    void getPriceIfNull() throws MarketDataNotFound {
        String body = """
        {
            "id": "bitcoin",
            "symbol": "btc",
            "name": "Bitcoin",
            "market_data": {
                "current_price": {
                    "eur": 11345.8976447824,
                    "bch": 5.76928286478153
                }
            }
        }
        """;
        CoinsHistoryByIdOnlyCurrentPrice bitcoinHistory = CoinsHistoryByIdOnlyCurrentPrice.deserialize(body);
        String message = "";
        try {
            bitcoinHistory.getPrice("none");
        } catch (CurrencyNotFoundException exception) {
            message = exception.getMessage();
        }
        assertEquals("'none' not found on Coingecko", message);
    }
}