package org.jugistanbul.time.adjuster;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.chrono.HijrahDate;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public NextFullWorkingDay(final List<Holiday> publicHolidays) {
        this.publicHolidays = publicHolidays;
    }

    @Override
    public Temporal adjustInto(final Temporal temporal) {

        DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
        List<Holiday> holidays = combinePublicAndReligiousHolidays(temporal);

        int day = dow.getValue();
        int dayToAdd = day > 4
                ? 8 - day
                : 1;

        Temporal result = temporal.plus(dayToAdd, ChronoUnit.DAYS);
        int dayOfMonth = result.get(ChronoField.DAY_OF_MONTH);
        int month = result.get(ChronoField.MONTH_OF_YEAR);

        Holiday holiday = new Holiday(dayOfMonth, month);
        if(holidays.contains(holiday)){
            return adjustInto(result);
        }
        return result;
    }

    private List<Holiday> combinePublicAndReligiousHolidays(final Temporal temporal){
        int hijriYear = getHijriYear(temporal);
        List<Holiday> daysOfFeastOfRamadan = getDaysOfFeastOfRamadan(hijriYear);
        List<Holiday> daysOfEidAlAdha = getDaysOfEidAlAdha(hijriYear);
        daysOfEidAlAdha.addAll(publicHolidays);

        return Stream.concat(daysOfFeastOfRamadan.stream(),
                daysOfEidAlAdha.stream())
                .collect(Collectors.toList());
    }

    private int getHijriYear(final Temporal temporal){
        HijrahDate hijrahDate = HijrahDate.from(temporal);
        return hijrahDate.get(ChronoField.YEAR);
    }
    private List<Holiday> getDaysOfFeastOfRamadan(final int hijriYear){

        HijrahDate feastOfRamadan    = HijrahDate.of(hijriYear, 10, 1);
        LocalDate beforeEveofRamadan = LocalDate.from(feastOfRamadan).plus(-2, ChronoUnit.DAYS);
        List<Holiday> daysOfFeastOfRamadan = new ArrayList<>();

        IntStream.rangeClosed(1, 4).forEach(day -> {
            LocalDate date = beforeEveofRamadan.plus(day, ChronoUnit.DAYS);
            Holiday holiday = new Holiday(date.getDayOfMonth(), date.getMonthValue());

            if(!publicHolidays.contains(holiday))
                daysOfFeastOfRamadan.add(holiday);
        });

        return daysOfFeastOfRamadan;
    }

    private List<Holiday> getDaysOfEidAlAdha(final int hijriYear){

        HijrahDate beforeEveOfEidAlAdha = HijrahDate.of(hijriYear, 12, 8);
        List<Holiday> daysOfEidAlAdha = new ArrayList<>();

        IntStream.rangeClosed(1, 5).forEach(day -> {
            LocalDate date = LocalDate.from(beforeEveOfEidAlAdha).plus(day, ChronoUnit.DAYS);
            Holiday holiday = new Holiday(date.getDayOfMonth(), date.getMonthValue());

            if(!publicHolidays.contains(holiday))
                daysOfEidAlAdha.add(holiday);
        });

        return daysOfEidAlAdha;
    }
}
