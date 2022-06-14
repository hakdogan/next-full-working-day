package org.jugistanbul.time.adjuster;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;
import java.util.List;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 14.06.2022
 */
public class NextFullWorkingDay implements TemporalAdjuster
{
    private final List<Holiday> publicHolidays;

    public NextFullWorkingDay() {
        publicHolidays = Arrays.asList(new Holiday(1, 1),
                new Holiday(23, 4),
                new Holiday(1, 5),
                new Holiday(19, 5),
                new Holiday(15, 7),
                new Holiday(30, 8),
                new Holiday(28, 10),
                new Holiday(29, 10));
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {

        DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));

        int day = dow.getValue();
        int dayToAdd = day > 4
                ? 8 - day
                : 1;

        Temporal result = temporal.plus(dayToAdd, ChronoUnit.DAYS);
        int dayOfMonth = result.get(ChronoField.DAY_OF_MONTH);
        int month = result.get(ChronoField.MONTH_OF_YEAR);

        Holiday holiday = new Holiday(dayOfMonth, month);
        if(publicHolidays.contains(holiday)){
            return adjustInto(result.plus(1, ChronoUnit.DAYS));
        }
        return result;
    }
}
