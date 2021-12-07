package coingecko.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class CoingeckoPriceServiceCache {
    private String filename = "coingecko-price-service-cache.json";
    private File file;
    private final HashMap<String, Double> cache = new HashMap<>();

    CoingeckoPriceServiceCache() throws IOException, org.json.simple.parser.ParseException {
        setFile();
        readMap();
    }

    CoingeckoPriceServiceCache(String filename) throws IOException, org.json.simple.parser.ParseException {
        setFilename(filename);
        setFile();
        readMap();
    }

    public void writeMap(Map<String, Double> map) throws IOException {
        try (Writer out = new FileWriter(getFile())) {
            JSONObject.writeJSONString(map, out);
        }
    }

    private void readMap() throws org.json.simple.parser.ParseException {
        String jsonAsString = getFileContent(getFile(), "{}");
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonAsString);
        json.forEach((key, value) -> {
            getCache().put((String) key, (Double) value);
        });
    }

    public double getPriceFromCacheIfPresentElseMinus1(String baseCurrency, String quoteCurrency, Date date) {
        String key = CacheEntry.formatKey(baseCurrency, quoteCurrency, date);
        boolean isInCache = getCache().keySet().stream()
                .anyMatch(_key -> _key.equals(key));
        if (isInCache) {
            return getCache().get(key);
        }
        return -1;
    }

    public void addToCache(String baseCurrency, String quoteCurrency, Date date, double price) throws IOException {
        CacheEntry entry = new CacheEntry(baseCurrency, quoteCurrency, date, price);
        getCache().put(entry.formatKey(), entry.getPrice());
        writeMap(getCache());
    }

    /**
     * Get byte array from an InputStream most efficiently.
     * Taken from sun.misc.IOUtils
     * @param is InputStream
     * @param length Length of the buffer, -1 to read the whole stream
     * @param readAll Whether to read the whole stream
     * @return Desired byte array
     * @throws IOException If maximum capacity exceeded.
     */
    public static byte[] readFully(InputStream is, int length, boolean readAll)
            throws IOException {
        byte[] output = {};
        if (length == -1) length = Integer.MAX_VALUE;
        int pos = 0;
        while (pos < length) {
            int bytesToRead;
            if (pos >= output.length) {
                bytesToRead = Math.min(length - pos, output.length + 1024);
                if (output.length < pos + bytesToRead) {
                    output = Arrays.copyOf(output, pos + bytesToRead);
                }
            } else {
                bytesToRead = output.length - pos;
            }
            int cc = is.read(output, pos, bytesToRead);
            if (cc < 0) {
                if (readAll && length != Integer.MAX_VALUE) {
                    throw new EOFException("Detect premature EOF");
                } else {
                    if (output.length != pos) {
                        output = Arrays.copyOf(output, pos);
                    }
                    break;
                }
            }
            pos += cc;
        }
        return output;
    }

    /**
     * Read the full content of a file.
     * @param file The file to be read
     * @param emptyValue Empty value if no content has found
     * @return File content as string
     */
    public static String getFileContent(File file, String emptyValue) {
        if (file.isDirectory()) return emptyValue;
        try {
            String content = new String(readFully(new FileInputStream(file), -1, true), Charset.defaultCharset());
            if (content.isEmpty() || content.isBlank()) return emptyValue;
            else return content;
        } catch (IOException e) {
            e.printStackTrace();
            return emptyValue;
        }
    }

    public HashMap<String, Double> getCache() {
        return this.cache;
    }

    private File getFile() {
        return this.file;
    }

    private void setFile() throws IOException {
        File cache = new File(filename);
        if (!cache.exists() || !cache.isFile()) {
            if (!cache.createNewFile() && !cache.canWrite() && !cache.canRead()) {
                throw new IOException("can not create, read or write cache file");
            }
        }
        this.file = cache;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}