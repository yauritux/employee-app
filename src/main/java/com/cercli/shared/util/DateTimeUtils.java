package com.cercli.shared.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class DateTimeUtils {

    private static final ZoneId SERVER_TIME_ZONE = ZoneId.of("Asia/Dubai");

    public static ZonedDateTime getCurrentDateTimeInServerTimeZone() {
        return ZonedDateTime.now(SERVER_TIME_ZONE);
    }

    public static ZonedDateTime convertToLocalDateTime(ZonedDateTime dateTime) {
        return dateTime.withZoneSameInstant(ZoneId.systemDefault());
    }
}
