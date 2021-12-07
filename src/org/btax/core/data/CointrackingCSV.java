package org.btax.core.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

public class CointrackingCSV {

    List<CointrackingCSVRow> rows;
    public static final CSVFormat COINTRACKING_FORMAT = CSVFormat.EXCEL;

    public CointrackingCSV(String data) throws IllegalArgumentException, IOException {
        setRows(data);
    }

    public CointrackingCSV(File file) throws IllegalArgumentException, IOException {
        setRows(file);
    }

    static CointrackingCSVRow recordToRow(CSVRecord record) {
        return new CointrackingCSVRow(
                record.get(0),
                record.get(1),
                record.get(2),
                record.get(3),
                record.get(4),
                record.get(5),
                record.get(6),
                record.get(7),
                record.get(8),
                record.get(9),
                record.get(10),
                record.toString()
        );
    }

    private void setRows(String data) throws IOException {
        CSVParser parser = CSVParser.parse(data, COINTRACKING_FORMAT);
        rows = parser.getRecords().stream()
                .map(CointrackingCSV::recordToRow)
                .collect(Collectors.toList());
    }

    private void setRows(File file) throws IllegalArgumentException, IOException {
        CSVParser parser = CSVParser.parse(file, Charset.defaultCharset(), COINTRACKING_FORMAT);
        rows = parser.getRecords().stream()
                .map(CointrackingCSV::recordToRow)
                .collect(Collectors.toList());
    }

    public List<CointrackingCSVRow> getRows() {
        return rows;
    }
}
