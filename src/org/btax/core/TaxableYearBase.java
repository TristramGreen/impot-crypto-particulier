package org.btax.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract public class TaxableYearBase implements TaxableYear {
    Bookkeeping bookkeeping;
    private int year;
    Date startLimit;
    Date endLimit;
    double taxableGain;

    TaxableYearBase(Bookkeeping bookkeeping, int year) {
        setBookkeeping(bookkeeping);
        setYear(year);
        setEndLimit();
        setStartLimit();
    }

    public void setStartLimit() {
        try {
            startLimit = new SimpleDateFormat("yyyy").parse(String.valueOf(getYear()));
        } catch(ParseException e) {e.printStackTrace();}
    }

    public void setEndLimit() {
        try {
            endLimit = new SimpleDateFormat("yyyy").parse(String.valueOf(getYear()+1));
        } catch(ParseException e) {e.printStackTrace();}
    }

    public Bookkeeping getBookkeeping() {
        return bookkeeping;
    }

    public void setBookkeeping(Bookkeeping bookkeeping) {
        this.bookkeeping = bookkeeping;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getStartLimit() {
        return startLimit;
    }

    public Date getEndLimit() {
        return endLimit;
    }

    public double getTaxableGain() {
        return taxableGain;
    }
}