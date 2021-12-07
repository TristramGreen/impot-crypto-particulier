package coingecko.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CacheEntry {
    private String baseCurrency;
    private String quoteCurrency;
    private Date date;
    private double price;
    public static SimpleDateFormat SERIALIZED_DATE_FORMAT = new SimpleDateFormat("dd_MM_yyyy");

    CacheEntry(String baseCurrency, String quoteCurrency, Date date, double price) {
        setBaseCurrency(baseCurrency);
        setQuoteCurrency(quoteCurrency);
        setDate(date);
        setPrice(price);
    }

    CacheEntry(Map.Entry<String, Double> entry) throws ParseException {
        String[] parts = entry.getKey().split("-");
        setBaseCurrency(parts[0]);
        setQuoteCurrency(parts[1]);
        setDate(deserializeDate(parts[2]));
        setPrice(entry.getValue());
    }

    CacheEntry(String serialized) throws ParseException {
        String[] parts = serialized.split("-");
        setBaseCurrency(parts[0]);
        setQuoteCurrency(parts[1]);
        setDate(deserializeDate(parts[2]));
        setPrice(Double.parseDouble(parts[3]));
    }

    public static String serializeDate(Date date) {
       return SERIALIZED_DATE_FORMAT.format(date);
    }

    public static Date deserializeDate(String date) throws ParseException {
        return SERIALIZED_DATE_FORMAT.parse(date);
    }

    public String formatKey() {
        return getBaseCurrency()
                .concat("-")
                .concat(getQuoteCurrency())
                .concat("-")
                .concat(serializeDate(getDate()))
                .replaceAll("\\s", "")
                .toUpperCase();
    }

    public static String formatKey(String baseCurrency, String quoteCurrency, Date date) {
        return baseCurrency
                .concat("-")
                .concat(quoteCurrency)
                .concat("-")
                .concat(serializeDate(date))
                .replaceAll("\\s", "")
                .toUpperCase();
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}