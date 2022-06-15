package org.jugistanbul.time.helper;

import org.jugistanbul.time.adjuster.NextFullWorkingDay;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 15.06.2022
 */
public class TimeHelper
{
    private static final NextFullWorkingDay WORKING_DAY = new NextFullWorkingDay();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String findNextFullWorkingDate(final String date) {

        return LocalDate.parse(date)
                .with(WORKING_DAY::adjustInto)
                .format(FORMATTER);
    }

    public static String findNextFullWorkingDate(final LocalDate date) {
        return date.with(WORKING_DAY::adjustInto).format(FORMATTER);
    }
}

