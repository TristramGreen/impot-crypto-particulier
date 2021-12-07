package org.btax;

import org.btax.core.Handover;
import org.btax.core.TaxableYearPost2019;
import org.btax.core.TaxableYearPre2019;
import org.btax.core.inventory.Inventory;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class View {

    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void displayTaxableYearPost2019(TaxableYearPost2019 taxableYearPost2019) {
        String template = "-- Year {0} ---------------------------------\n////////////////////////////////////////////// ";
        template = MessageFormat.format(template, String.valueOf(taxableYearPost2019.getYear()));
        System.out.println(template);
        int index = 1;
        for (Handover handover : taxableYearPost2019.getHandovers()) {
            System.out.println(formatHandover(handover, index++));
        }
        template = "Year {0} Taxable Gain = {1}";
        template = MessageFormat.format(template, String.valueOf(taxableYearPost2019.getYear()),
                decimalFormat.format(taxableYearPost2019.getTaxableGain()));
        System.out.println(template);
    }

    public static void displayTaxableYearPre2019(TaxableYearPre2019 taxableYearPre2019) {
        String template = "-- Year {0} ---------------------------------\n//////////////////////////////////////////////\nYear {0} Taxable Gain = {1}";
        template = MessageFormat.format(template, String.valueOf(taxableYearPre2019.getYear()),
                decimalFormat.format(taxableYearPre2019.getTaxableGain()));
        System.out.println(template);
    }

    public static void displayInventories(List<Inventory> inventories) {
        System.out.println("-- Inventories -------------------------------\n//////////////////////////////////////////////");
        inventories.forEach(inventory -> System.out.println(inventory.getCurrency()+" : "+decimalFormat.format(inventory.quantity())));
    }

    public static String formatHandover(Handover handover, int index) {
        String template = "";
        template += "Handover #{12}";
        template += "\n";
        template += "211 - Date ------------------------------ {13}\n";
        template += "212 - Global Value ---------------------- {0}\n";
        template += "\n";
        template += "213 - Price ----------------------------- {1}\n";
        template += "214 - Fee ------------------------------- {2}\n";
        template += "215 - Fee Free Price -------------------- {3}\n";
        template += "216 - Adjustment ------------------------ {4}\n";
        template += "217 - Adjustment Free Price ------------- {5}\n";
        template += "218 - Adjustment And Fee Free Price ----- {6}\n";
        template += "\n";
        template += "220 - Global Cost ----------------------- {7}\n";
        template += "221 - Net Cost To Initial Capital Sum --- {8}\n";
        template += "222 - Benefits Before Handover ---------- {9}\n";
        template += "223 - Net Cost -------------------------- {10}\n";
        template += "\n";
        template += "Taxable Gain ---------------------------- {11}";
        template += "\n";


        Object[] params = new Object[]{
                decimalFormat.format(handover.getGlobalValue()),
                decimalFormat.format(handover.getPrice()),
                decimalFormat.format(handover.getFee()),
                decimalFormat.format(handover.feeFreePrice()),
                decimalFormat.format(handover.getAdjustment()),
                decimalFormat.format(handover.adjustmentFreePrice()),
                decimalFormat.format(handover.adjustmentAndFeeFreePrice()),
                decimalFormat.format(handover.getGlobalCost()),
                decimalFormat.format(handover.getNetCostToInitialCapitalSum()),
                decimalFormat.format(handover.getAdjustmentBenefitsBeforeHandover()),
                decimalFormat.format(handover.netCost()),
                decimalFormat.format(handover.taxableGain()),
                String.valueOf(index),
                dateFormat.format(handover.getDate())
        };
        return MessageFormat.format(template, params);
    }
}
