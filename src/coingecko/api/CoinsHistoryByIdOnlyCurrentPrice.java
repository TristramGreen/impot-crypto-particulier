package coingecko.api;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;

public class CoinsHistoryByIdOnlyCurrentPrice {
    public String id;
    public String symbol;
    public String name;
    public Map<String, Double> currentPrice;

    public CoinsHistoryByIdOnlyCurrentPrice(String id, String symbol, String name, Map<String, Double> currentPrice) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public static CoinsHistoryByIdOnlyCurrentPrice deserialize(String body) throws MarketDataNotFound {
        System.out.println(body);
        JSONObject object = CoingeckoClient.parseJSONObject(body);
        String id = (String) object.get("id");
        String symbol = (String) object.get("symbol");
        String name = (String) object.get("name");
        JSONObject _currentPrice = null;
        try {
            JSONObject marketData = (JSONObject) object.get("market_data");
            _currentPrice = (JSONObject) marketData.get("current_price");
        } catch (NullPointerException exception) {
            throw new MarketDataNotFound();
        }
        JSONObject final_currentPrice = _currentPrice;
        HashMap<String, Double> currentPrice = (HashMap<String, Double>) _currentPrice.keySet().stream()
                .collect(Collectors.toMap(
                        x -> (String) x,
                        x -> {
                            String value = final_currentPrice.get(x).toString();
                            return parseDouble(value);
                        }));
        return new CoinsHistoryByIdOnlyCurrentPrice(id, symbol, name, currentPrice);
    }

    public double getPrice(String quoteCurrency) throws CurrencyNotFoundException {
        double price = 0;
        try {
            price = this.currentPrice.get(quoteCurrency.toLowerCase());
        } catch (NullPointerException exception) {
            throw new CurrencyNotFoundException(quoteCurrency);
        }
        return  price;
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }
}
