package vn.edu.nlu.web.chat.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateUtils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_2 = "dd/MM/yyyy";
    public static final String DATE_FORMAT_3 = "dd-MM-yyyy";
    public static final String DATE_FORMAT_4 = "yyyy/MM/dd";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT2 = "dd/MM/yyyy HH:mm:ss";
    // Constants for various date formats
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String US_DATE_FORMAT = "MM/dd/yyyy";
    public static final String EUROPEAN_DATE_FORMAT = "dd/MM/yyyy";
    public static final String FULL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FULL_DATETIME_FORMAT_WITH_MILLIS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * Converts a String to a Date object.
     *
     * @param dateString the string representing the date
     * @param format     the format of the date string (e.g., "yyyy-MM-dd")
     * @return the Date object parsed from the string
     * @throws ParseException if the string cannot be parsed as a date
     */
    public static Date stringToDate(String dateString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }

    /**
     * Converts a Date object to a String.
     *
     * @param date   the Date object to convert
     * @param format the format of the output string (e.g., "yyyy-MM-dd")
     * @return the string representation of the date
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * Calculates the difference between two dates in milliseconds.
     *
     * @param date1 the first date
     * @param date2 the second date
     * @return the difference between date1 and date2 in milliseconds
     */
    public static long diffInMilliseconds(Date date1, Date date2) {
        return Math.abs(date1.getTime() - date2.getTime());
    }

    /**
     * Validates a date string based on the provided format.
     *
     * @param dateString the string representing the date
     * @param format     the format of the date string (e.g., "yyyy-MM-dd")
     * @throws ParseException if the string is invalid
     */
    public static void validateDateString(String dateString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false); // Strictly enforce format
        dateFormat.parse(dateString);
    }

    /**
     * Converts a String to a Date object, handling potential parsing errors.
     *
     * @param dateString the string representing the date
     * @param format     the format of the date string (e.g., "yyyy-MM-dd")
     * @return the Date object parsed from the string, or null if parsing fails
     */
    public static Date safeStringToDate(String dateString, String format) {
        try {
            validateDateString(dateString, format);
            return stringToDate(dateString, format);
        } catch (ParseException e) {
            return null; // Handle parsing error gracefully
        }
    }


    /**
     * Checks if a date falls within a specific date range (inclusive).
     *
     * @param date      the date to check
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @return true if the date is within the range, false otherwise
     * @throws IllegalArgumentException if any date is null
     */
    public static boolean isDateInRange(Date date, Date startDate, Date endDate) throws IllegalArgumentException {
        if (date == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("All dates must be provided");
        }
        return date.getTime() >= startDate.getTime() && date.getTime() <= endDate.getTime();
    }

    /**
     * Gets the day of the week for a given date.
     *
     * @param date the date
     * @return the day of the week (e.g., Calendar.SUNDAY)
     * @throws IllegalArgumentException if date is null
     */
    public static int getDayOfWeek(Date date) throws IllegalArgumentException {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Converts a String to a LocalDate object.
     *
     * @param dateString the string representing the date
     * @param format     the format of the date string (e.g., "yyyy-MM-dd")
     * @return the LocalDate object parsed from the string
     * @throws java.time.format.DateTimeParseException if the string cannot be parsed as a date
     */
    public static LocalDate stringToLocalDate(String dateString, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateString, dateTimeFormatter);
    }


    /**
     * Calculates the difference between two LocalDate objects in days.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the difference between endDate and startDate in days
     */
    public static long diffInDays(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * Converts a LocalDate object to a String.
     *
     * @param localDate the LocalDate object to convert
     * @param format    the format of the output string (e.g., "yyyy-MM-dd")
     * @return the string representation of the date
     */
    public static String localDateToString(LocalDate localDate, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * Converts a String to a LocalDateTime object.
     *
     * @param dateTimeString the string representing the date and time
     * @param format         the format of the date-time string (e.g., "yyyy-MM-dd HH:mm:ss")
     * @return the LocalDateTime object parsed from the string
     * @throws java.time.format.DateTimeParseException if the string cannot be parsed
     */
    public static LocalDateTime stringToLocalDateTime(String dateTimeString, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
    }

    /**
     * Converts a LocalDateTime object to a String.
     *
     * @param localDateTime the LocalDateTime object to convert
     * @param format        the format of the output string (e.g., "yyyy-MM-dd HH:mm:ss")
     * @return the string representation of the date and time
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * Converts an Instant object to a LocalDate object.
     *
     * @param instant the Instant object representing a point in time
     * @return the LocalDate object at midnight on the date represented by the Instant
     */
    public static LocalDate instantToLocalDate(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Converts an Instant object to a LocalDateTime object.
     *
     * @param instant the Instant object representing a point in time
     * @return the LocalDateTime object representing the date and time of the Instant
     */
    public static LocalDateTime instantToLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * Gets the first day of the month for a given LocalDate.
     *
     * @param date the date
     * @return the first day of the month
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.withDayOfMonth(1);
    }

    /**
     * Gets the last day of the month for a given LocalDate.
     *
     * @param date the date
     * @return the last day of the month
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * Calculates the age based on the provided birthdate.
     *
     * @param birthDate the birthdate
     * @return the age in years
     */
    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Converts a String to an Instant object, handling potential parsing errors.
     *
     * @param dateString the string representing the date and time (format depends on parser)
     * @param format     a hint about the format of the string (e.g., "yyyy-MM-dd HH:mm:ss")
     * @return the Instant object parsed from the string, or null if parsing fails
     */
    public static Instant safeStringToInstant(String dateString, String format) {
        try {
            // Attempt parsing with basic formatters for common formats
            if (format == null || format.isEmpty()) {
                try {
                    return Instant.parse(dateString); // Try basic ISO-8601 format
                } catch (DateTimeParseException e) {
                    // Ignore exception and try other formats
                }
            }
            switch (Objects.requireNonNull(format)) {
                case DateUtils.FULL_DATETIME_FORMAT, DateUtils.FULL_DATETIME_FORMAT_WITH_MILLIS -> {
                    return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(format)).toInstant((ZoneOffset) ZoneId.systemDefault());
                }
                case DateUtils.TIME_FORMAT -> {
                    // Handle time-only format (assuming current date)
                    LocalDate today = LocalDate.now();
                    LocalDateTime dateTime = LocalDateTime.of(today, LocalTime.parse(dateString, DateTimeFormatter.ofPattern(format)));
                    return dateTime.toInstant((ZoneOffset) ZoneId.systemDefault());
                }
            }
            // If specific format is provided and doesn't match common ones, use it directly
            return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(format)).toInstant((ZoneOffset) ZoneId.systemDefault());
        } catch (DateTimeParseException e) {
            return null; // Handle parsing error gracefully
        }
    }


}
