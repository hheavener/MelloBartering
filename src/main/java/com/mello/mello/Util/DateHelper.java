package com.mello.mello.Util;

import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateHelper {


    public static String getTimeElapsed(LocalDateTime date) {
        if (date == null) return null;
        LocalDate postdate = date.toLocalDate();
        LocalDate now = LocalDateTime.now().toLocalDate();

        int years_passed = (int) ChronoUnit.YEARS.between(postdate, now);
        int months_passed = (int) ChronoUnit.MONTHS.between(postdate, now);
        int weeks_passed = (int) ChronoUnit.WEEKS.between(postdate, now);
        int days_passed = (int) ChronoUnit.DAYS.between(postdate, now);

        if (years_passed > 0) {
            return years_passed == 1 ? years_passed + " year ago" : years_passed + " year ago";
        } else if (months_passed > 0) {
            return months_passed == 1 ? months_passed + " month ago" : months_passed + " months ago";
        } else if (weeks_passed > 0) {
            return weeks_passed == 1 ? weeks_passed + " week ago" : weeks_passed + " weeks ago";
        } else if (days_passed > 0) {
            return days_passed == 1 ? days_passed + " day ago" : days_passed + " days ago";
        } else {
            return "Today";
        }
    }

    public static String formatLocalDateTime(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - hh:mm a");
        return date.format(formatter);
    }
}
