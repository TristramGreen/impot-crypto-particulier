package coingecko.api;

public class CurrencyNotFoundException extends Exception {

    String currency;

    public CurrencyNotFoundException(String currency) {
        this.currency = currency;
    }

    @Override
    public String getMessage() {
        return "'"+this.currency+"' not found on Coingecko";
    }
}