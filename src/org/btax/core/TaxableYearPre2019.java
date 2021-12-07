package org.btax.core;

import org.btax.core.inventory.Inventory;

import java.util.List;
import java.util.stream.Collectors;

public class TaxableYearPre2019 extends TaxableYearBase {

    public TaxableYearPre2019(Bookkeeping bookkeeping, int year) {
        super(bookkeeping, year);
        setTaxableGain();
    }

    public void setTaxableGain() {
        List<Inventory> inventoriesStateAtStart = getBookkeeping().getInventories().stream()
                .map(inventory -> new FIFO(inventory, getStartLimit()).getInventory())
                .collect(Collectors.toList());

        this.taxableGain = inventoriesStateAtStart.stream()
                .map(inventory -> new FIFO(inventory, getEndLimit()).getGain())
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}