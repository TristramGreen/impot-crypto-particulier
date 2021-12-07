package org.btax.core.data;

import org.btax.core.data.CointrackingCSV;
import org.btax.core.data.CointrackingCSVRow;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class CointrackingCSVTest {
    String data = """
            "Withdrawal","","","10.00000000","BTC","","","Kraken","","","2021-01-25 00:00:00"
            "Deposit","10.00000000","BTC","","","","","PaperWallet1","","","2021-01-25 00:00:00"
            "Lost","","","20.00000000","ETH","","","","","","2021-01-01 01:00:00"
            "Stolen","","","10.00000000","BTC","","","","","","2020-01-20 00:00:00"
            "Donation","","","1.00000000","ETH","0.00010000","ETH","","","","2019-03-20 00:00:00"
            "Gift","","","1.00000000","ETH","","","","","","2019-03-20 00:00:00"
            "Spend","","","1.00000000","BTC","0.00010000","BTC","","","[servicePrice=500.00,serviceDescription=proxy servers]","2019-02-01 00:00:00"
            "Mining","0.10000000","BTC","","","0.00010000","BTC","","","","2019-01-21 00:00:00"
            "Mining (commercial)","0.10000000","BTC","","","0.00010000","BTC","","","","2019-01-21 00:00:00"
            "Airdrop","100.00000000","EOS","","","","","","","","2019-01-21 00:00:00"
            "Staking","100.00000000","XTZ","","","","","","","","2019-01-21 00:00:00"
            "Masternode","100.00000000","DASH","","","","","","","","2019-01-21 00:00:00"
            "Minting","100.00000000","BURST","","","","","","","","2019-01-21 00:00:00"
            "Reward / Bonus","1.00000000","ETH","","","","","","","","2019-01-20 14:53:03"
            "Gift/Tip","1.00000000","ETH","","","","","","","guy at the bar","2019-01-20 01:00:00"
            "Income","1.00000000","BTC","","","","","","salary","","2019-01-20 00:00:00"
            "Trade","200.00000000","EUR","10.00000000","BTC","1.00000000","EUR","Kraken","","","2017-01-02 00:00:00"
            "Trade","200.00000000","ETH","200.00000000","EUR","1.00000000","EUR","Kraken","","","2017-01-01 02:00:00"
            "Deposit","100.00000000","BTC","","","","","Coinbase","","","2017-01-01 01:50:01"
            "Withdrawal","","","100.00000000","BTC","0.00010000","BTC","Kraken","","","2017-01-01 01:10:00"
            "Trade","100.00000000","BTC","1000.00000000","EUR","1.00000000","EUR","Kraken","","Wow quelle affaire!","2017-01-01 01:00:00"
            "Deposit","1200.00000000","EUR","","","","","Kraken","","","2017-01-01 00:50:00"
            """;

    @Test
    void creation() throws IOException {
        CointrackingCSV csv = new CointrackingCSV(data);

        assertEquals(22, csv.getRows().size());

        CointrackingCSVRow row = csv.getRows().get(0);

        assertEquals("Withdrawal", row.getType());
        assertEquals("", row.getIncoming());
        assertEquals("", row.getIncomingCurrency());
        assertEquals("10.00000000", row.getOutgoing());
        assertEquals("BTC", row.getOutgoingCurrency());
        assertEquals("", row.getFee());
        assertEquals("", row.getFeeCurrency());
        assertEquals("Kraken", row.getExchange());
        assertEquals("", row.getGroup());
        assertEquals("", row.getComment());
        assertEquals("2021-01-25 00:00:00", row.getDate());
        assertEquals("CSVRecord [comment='null', recordNumber=1, values=[Withdrawal, , , 10.00000000, BTC, , , Kraken, , , 2021-01-25 00:00:00]]", row.getRecordString());
    }
}