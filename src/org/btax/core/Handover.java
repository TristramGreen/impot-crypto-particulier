package org.btax.core;

import java.util.Date;

public class Handover {
    Date date;//211
    double globalValue;//212
    // get Price
    double price; //213
    double fee; //214
    public double feeFreePrice() /* 215 */ {  return price - fee; }
    double adjustment;//216
    public double adjustmentFreePrice() /* 217 */ { return price - adjustment; }
    public double adjustmentAndFeeFreePrice() /* 218 */ { return price - fee - adjustment; }
    // get Costs
    double globalCost; //220
    double netCostToInitialCapitalSum; // 221
    public double netCostToInitialCapital()  { return netCost() * adjustmentFreePrice() / globalValue; }
    double adjustmentBenefitsBeforeHandover; //222
    public double netCost() /* 223 */ {  return globalCost - netCostToInitialCapitalSum - adjustmentBenefitsBeforeHandover; }

    public double taxableGain() {
        return adjustmentAndFeeFreePrice() - netCostToInitialCapital();
    }

    public Handover(Date date, double netCostToInitialCapitalSum, double globalValue, double price, double fee, double adjustment, double globalCost, double adjustmentBenefitsBeforeHandover) {
        setDate(date);
        setGlobalValue(globalValue);
        setPrice(price);
        setFee(fee);
        setAdjustment(adjustment);
        setNetCostToInitialCapitalSum(netCostToInitialCapitalSum);
        setGlobalCost(globalCost);
        setAdjustmentBenefitsBeforeHandover(adjustmentBenefitsBeforeHandover);
    }

    public void setGlobalCost(double globalCost) {
        this.globalCost = globalCost;
    }

    public double getNetCostToInitialCapitalSum() {
        return netCostToInitialCapitalSum;
    }

    public void setNetCostToInitialCapitalSum(double netCostToInitialCapitalSum) {
        this.netCostToInitialCapitalSum = netCostToInitialCapitalSum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getGlobalValue() {
        return globalValue;
    }

    public void setGlobalValue(double globalValue) {
        this.globalValue = globalValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(double adjustment) {
        this.adjustment = adjustment;
    }

    public double getGlobalCost() {
        return globalCost;
    }

    public double getAdjustmentBenefitsBeforeHandover() {
        return adjustmentBenefitsBeforeHandover;
    }

    public void setAdjustmentBenefitsBeforeHandover(double adjustmentBenefitsBeforeHandover) {
        this.adjustmentBenefitsBeforeHandover = adjustmentBenefitsBeforeHandover;
    }

}