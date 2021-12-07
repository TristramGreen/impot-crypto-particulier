package coingecko.api;

public class MarketDataNotFound extends Exception {

    String currency;
    String date;

    public MarketDataNotFound() {
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getMessage() {
        return currency+" "+date+": Market data not found on Coingecko's history";
    }
}