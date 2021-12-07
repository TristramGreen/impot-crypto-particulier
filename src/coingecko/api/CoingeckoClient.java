package coingecko.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

public class CoingeckoClient {
    HttpClient client;
    HttpRequest request;

    public CoingeckoClient() {
        setClient(HttpClient.newHttpClient());
    }

    public CoinsHistoryByIdOnlyCurrentPrice getCoinsHistoryByIdOnlyCurrentPrice(String id, String date)
            throws IOException, InterruptedException {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("date", date);
        String uri = "https://api.coingecko.com/api/v3/coins/"+id+"/history"+getParamsString(paramsMap);
        System.out.println(uri);
        request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        while(response.statusCode() == 504) {
            System.out.println("Timeout: Retry : "+uri);
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        try {
            return CoinsHistoryByIdOnlyCurrentPrice.deserialize(response.body());
        } catch (MarketDataNotFound marketDataNotFound) {
            marketDataNotFound.setCurrency(id);
            marketDataNotFound.setDate(date);
            System.out.println("ERROR : "+marketDataNotFound.getMessage());
            System.exit(1);
        }
        return null;
    }

    public List<CoinsList> getCoinsList() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.coingecko.com/api/v3/coins/list"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return CoinsList.deserialize(response.body());
    }

    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        if (params.size() == 0) {
            return "";
        } else {
            result.append("?");
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }
        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    public static JSONObject parseJSONObject(String body) {
        JSONObject object = null;
        try {
            JSONParser parser = new JSONParser();
            object = (JSONObject) parser.parse(body);
        } catch (ParseException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
        return object;
    }

    public static JSONArray parseJSONArray(String body) {
        JSONArray array = null;
        try {
            JSONParser parser = new JSONParser();
            array = (JSONArray) parser.parse(body);
        } catch (ParseException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
        return array;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }
}
