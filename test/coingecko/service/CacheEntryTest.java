package coingecko.service;

import org.btax.core.TransactionDate;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CacheEntryTest {
    @Test
    void testDateFormat() throws Exception {
        Date date = TransactionDate.parse("2019-07-27 14:00:00");
        String stringDate = CacheEntry.serializeDate(date);
        assertEquals("27_07_2019", stringDate);
    }
}