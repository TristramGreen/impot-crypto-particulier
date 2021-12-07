package coingecko.service;

import coingecko.api.CoingeckoClient;
import coingecko.api.CoinsHistoryByIdOnlyCurrentPrice;
import coingecko.api.CoinsList;
import coingecko.api.CurrencyNotFoundException;
import org.btax.core.Bookkeeping;
import org.btax.core.PriceService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CoingeckoPriceService implements PriceService {
    static final SimpleDateFormat COINGECKO_SNAPSHOT_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    CoingeckoClient coingeckoClient = new CoingeckoClient();
    CoingeckoPriceServiceCache coingeckoPriceServiceCache = new CoingeckoPriceServiceCache();
    List<CoinsList> coinsLists = new ArrayList<>();
    HashMap<String, Double> cachePrice = new HashMap<>();

    CoingeckoPriceService(CoingeckoClient coingeckoClient) throws IOException, InterruptedException, org.json.simple.parser.ParseException {
        setClient(coingeckoClient);
        setCoinsLists();
    }

    public CoingeckoPriceService() throws IOException, InterruptedException, ParseException, org.json.simple.parser.ParseException {
        setCoinsLists();
    }

    @Override
    public double getPrice(String baseCurrency, String quoteCurrency, Date date) throws Exception {
        date = resolutionAdjustedDate(date);
        double price = getPriceByAnyMean(baseCurrency, quoteCurrency, date);
        return price;
    }

    // coingecko just has daily price data so we normalize every date
    public static Date resolutionAdjustedDate(Date date) throws ParseException {
        return COINGECKO_SNAPSHOT_DATE_FORMAT.parse( COINGECKO_SNAPSHOT_DATE_FORMAT.format(date) );
    }

    private double getPriceByAnyMean(String baseCurrency, String quoteCurrency, Date date) throws Exception {
        if (baseCurrency.equalsIgnoreCase(Bookkeeping.CURRENCY)) System.err.println("accounting currency should be the quote currency");
        double price = coingeckoPriceServiceCache.getPriceFromCacheIfPresentElseMinus1(baseCurrency, quoteCurrency, date);
        if (price == (-1)) {
            price = getPriceFromApi(baseCurrency, quoteCurrency, date);
        }
        return price;
    }

    private double getPriceFromApi(String baseCurrency, String quoteCurrency, Date date) throws Exception {
        double price = 0;
        String formattedDate = COINGECKO_SNAPSHOT_DATE_FORMAT.format(date);
        CoinsHistoryByIdOnlyCurrentPrice history = getClient().getCoinsHistoryByIdOnlyCurrentPrice(getId(baseCurrency), formattedDate);
        // rate limit
        Thread.sleep(1000);
        price = history.getPrice(quoteCurrency);
        coingeckoPriceServiceCache.addToCache(baseCurrency, quoteCurrency, date, price);
        return price;
    }

    String getId(String currency) throws CurrencyNotFoundException {
        Optional<CoinsList> coin = getCoinsLists().stream()
                .filter(coinsList -> coinsList.getSymbol().equalsIgnoreCase(currency))
                .findFirst();
        if(coin.isEmpty()) throw new CurrencyNotFoundException(currency);
        return coin.get().getId();
    }

    public List<CoinsList> getCoinsLists() {
        return coinsLists;
    }

    public void setCoinsLists() throws InterruptedException, IOException {
        this.coinsLists = getClient().getCoinsList();
        // rate limit
        Thread.sleep(1000);
    }

    public CoingeckoClient getClient() {
        return coingeckoClient;
    }

    public void setClient(CoingeckoClient coingeckoClient) {
        this.coingeckoClient = coingeckoClient;
    }
}
