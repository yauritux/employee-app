package com.cercli.shared.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Utility class for handling date and time operations.
 */
public class DateTimeUtils {

    private static final ZoneId SERVER_TIME_ZONE = ZoneId.of("Asia/Dubai");

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
}
