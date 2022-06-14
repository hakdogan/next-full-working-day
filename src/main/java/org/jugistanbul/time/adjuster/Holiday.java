package org.jugistanbul.time.adjuster;

import java.util.Objects;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 14.06.2022
 */
class Holiday
{
    private int dayOfMonth;
    private int month;

    public Holiday(int dayOfMonth, int month) {
        this.dayOfMonth = dayOfMonth;
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holiday holiday = (Holiday) o;
        return dayOfMonth == holiday.dayOfMonth && month == holiday.month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfMonth, month);
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "dayOfMonth=" + dayOfMonth +
                ", month=" + month +
                '}';
    }
}