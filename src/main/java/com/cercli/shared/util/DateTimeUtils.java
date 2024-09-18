package com.cercli.shared.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Utility class for handling date and time operations.
 */
public class DateTimeUtils {

    public static final ZoneId SERVER_TIME_ZONE = ZoneId.of("Asia/Dubai");

    /**
     * Gets the current date and time in the server's time zone.
     *
     * @return the current date and time in the server's time zone.
     */
    public static ZonedDateTime getCurrentDateTimeInServerTimeZone() {
        return ZonedDateTime.now(SERVER_TIME_ZONE);
    }

    /**
     * Converts a date and time to the local time zone.
     *
     * @param dateTime the date and time to convert.
     * @return the date and time in the local time zone.
     */
    public static ZonedDateTime convertToLocalDateTime(ZonedDateTime dateTime) {
        return dateTime.withZoneSameInstant(ZoneId.systemDefault());
    }

    /**
     * Convert and return any date entered by the user into ZonedDateTime object.
     *
     * @param enteredDate date and time entered by the user in the format of yyyy-mm-dd hh:mm (e.g., 2024-09-18 07:30)
     * @return ZonedDateTime object
     */
    public static ZonedDateTime getZonedDateTime(String enteredDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
        return LocalDateTime.parse(enteredDate, dtf).atZone(DateTimeUtils.SERVER_TIME_ZONE);
    }
}
