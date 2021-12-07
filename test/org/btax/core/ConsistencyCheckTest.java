package org.btax.core;

import mock.PriceServiceMock1000;
import org.btax.core.data.CointrackingCSV;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConsistencyCheckTest {

    @Test
    void checkThereIsNoOverflow() throws IOException {
        String data = "";
        data += "\"Trade\",\"1\",\"BTC\",\"50\",\"EUR\",\"\",\"\",\"\",\"\",\"\",\"2017-01-01 00:00:00\"\n";
        data += "\"Trade\",\"50\",\"ETH\",\"1.3\",\"BTC\",\"\",\"\",\"\",\"\",\"\",\"2017-01-01 00:00:01\"";

        CointrackingCSV csv = new CointrackingCSV(data);
        Bookkeeping bookkeeping = Bookkeeping.createFromRows(csv.getRows(), new PriceServiceMock1000());

        try {
            new ConsistencyCheck(bookkeeping);
        } catch(InventoryOverflowException exception) {
            String result = """
                    
                    ERROR: Inventory Overflow Exception
                    CSVRecord [comment='null', recordNumber=2, values=[Trade, 50, ETH, 1.3, BTC, , , , , , 2017-01-01 00:00:01]]
                    1.30000000 outgoing BTC but the quantity in the inventory at this moment is 1.00000000
                    Difference = quantity - outgoing = -0.30000000""";

            assertEquals(result, exception.getMessage());
        }
    }
}