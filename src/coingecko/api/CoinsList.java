package coingecko.api;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoinsList {
    public String id;
    public String symbol;
    public String name;

    public CoinsList(String id, String symbol, String name) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
    }

    public static List<CoinsList> deserialize(String body) {
        Stream<JSONObject> stream = CoingeckoClient.parseJSONArray(body).stream();
        return stream
                .map(object -> {
                    String id = (String) object.get("id");
                    String symbol = (String) object.get("symbol");
                    String name = (String) object.get("name");
                    return new CoinsList(id, symbol, name);
                })
                .collect(Collectors.toList());
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
