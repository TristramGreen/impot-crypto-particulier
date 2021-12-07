package org.btax;

import coingecko.service.CoingeckoPriceService;
import org.btax.core.*;
import org.btax.core.data.CointrackingCSV;
import org.btax.core.data.CointrackingCSVRow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        // Parse given args
        CommandLine commandLine = new CommandLine(args);
        // Dependencies for fetching data
        File csvFile = createCsvFile(commandLine);
        PriceService priceService = new CoingeckoPriceService();
        // Bookkeeping models data and hold those
        Bookkeeping bookkeeping = createBookkeeping(csvFile, priceService);
        // Check data looks correct for accounting
        try {
            new ConsistencyCheck(bookkeeping);
        } catch (InventoryOverflowException exception) {
            System.out.print(exception.getMessage());
            System.exit(1);
        }

        new Wrapper(bookkeeping).displayAll();
    }

    public static File createCsvFile(CommandLine commandLine) throws Exception {
        File csv = new File(commandLine.getOptionValue("f"));
        if (!csv.exists() || !csv.isFile()) {
            throw new FileNotFoundException("csv file not found");
        }
        if (!csv.canRead()) {
            throw new Exception("csv file can not be read");
        }
        return csv;
    }

    public static Bookkeeping createBookkeeping(File file, PriceService service) throws IOException {
        CointrackingCSV cointrackingCSV = new CointrackingCSV(file);
        List<CointrackingCSVRow> rows = cointrackingCSV.getRows();
        return Bookkeeping.createFromRows(rows, service);
    }
}