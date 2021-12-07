package coingecko.api;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoinsListTest {
    @Test
    public void deserializeWithJSONArray() {
        String body  = """
                [
                  {
                    "id": "01coin",
                    "symbol": "zoc",
                    "name": "01coin"
                  },
                  {
                    "id": "0-5x-long-algorand-token",
                    "symbol": "algohalf",
                    "name": "0.5X Long Algorand Token"
                  },
                  {
                    "id": "0-5x-long-altcoin-index-token",
                    "symbol": "althalf",
                    "name": "0.5X Long Altcoin Index Token"
                  }
                ]
                """;
        List<CoinsList> coinsList = CoinsList.deserialize(body);
        assertEquals(3, coinsList.size());
        assertEquals("0-5x-long-altcoin-index-token", coinsList.get(2).id);
        assertEquals("zoc", coinsList.get(0).symbol);
        assertEquals("0.5X Long Algorand Token", coinsList.get(1).name);
    }
}