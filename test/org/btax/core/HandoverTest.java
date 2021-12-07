package org.btax.core;

import org.btax.core.data.transactions.TransactionBase;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class HandoverTest {

    @Test
    void handoverCreation() throws ParseException {
        Date date = TransactionBase.DATE_FORMATTER.parse("2019-01-09 09:00:00");

        Handover handover1 = new Handover(date, 0, 22464, 3744, 0, 0, 25351, 0);
        assertEquals(3744, handover1.feeFreePrice());
        assertEquals(3744, handover1.adjustmentAndFeeFreePrice());
        assertEquals(4225.166666666667, handover1.netCostToInitialCapital());
        assertEquals(25351, handover1.netCost());
        assertEquals(-481.16666666666697, handover1.taxableGain());

        Handover handover2 = new Handover(date, handover1.netCostToInitialCapital(), 63103, 224, 0, 0, 34856, 0);
        assertEquals(108.73186166531966, handover2.netCostToInitialCapital());
        assertEquals(115.26813833468034, handover2.taxableGain());
    }
}