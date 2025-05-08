package org.example.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int MIN_YEAR = 2024;
    private static final int MAX_YEAR = 2030;

    public static String getDateValidationMessage(String dateStr) {
        // Check if the string matches the basic format (YYYY-MM-DD)
        if (!dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return "Invalid date format. Please use YYYY-MM-DD format.";
        }

        // Split the date string to check individual components
        String[] parts = dateStr.split("-");
        if (parts.length != 3) {
            return "Invalid date format. Please use YYYY-MM-DD format.";
        }

        try {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            // Check year range
            if (year < MIN_YEAR || year > MAX_YEAR) {
                return String.format("Year must be between %d and %d.", MIN_YEAR, MAX_YEAR);
            }

            // Check month range
            if (month < 1 || month > 12) {
                return "Month must be between 01 and 12.";
            }

            // Check day range based on month
            int maxDays = getMaxDaysInMonth(year, month);
            if (day < 1 || day > maxDays) {
                return String.format("Day must be between 01 and %d for the selected month.", maxDays);
            }

            // Try to parse the full date to catch any other invalid combinations
            LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
            LocalDate today = LocalDate.now();

            // Check if date is in the past
            if (date.isBefore(today)) {
                return "Cannot make reservations for past dates.";
            }

            return null; // No error
        } catch (NumberFormatException e) {
            return "Invalid date format. Please use YYYY-MM-DD format.";
        } catch (DateTimeParseException e) {
            return "Invalid date. Please check the month and day values.";
        }
    }

    private static int getMaxDaysInMonth(int year, int month) {
        return switch (month) {
            case 2 -> isLeapYear(year) ? 29 : 28;
            case 4, 6, 9, 11 -> 30;
            default -> 31;
        };
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static boolean isValidDate(String dateStr) {
        return getDateValidationMessage(dateStr) == null;
    }

    public static String getDateRangeValidationMessage(String checkInStr, String checkOutStr) {
        String checkInMessage = getDateValidationMessage(checkInStr);
        if (checkInMessage != null) {
            return "Check-in date: " + checkInMessage;
        }

        String checkOutMessage = getDateValidationMessage(checkOutStr);
        if (checkOutMessage != null) {
            return "Check-out date: " + checkOutMessage;
        }

        try {
            LocalDate checkIn = LocalDate.parse(checkInStr, DATE_FORMATTER);
            LocalDate checkOut = LocalDate.parse(checkOutStr, DATE_FORMATTER);

            if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
                return "Check-out date must be after check-in date.";
            }

            if (checkIn.plusDays(30).isBefore(checkOut)) {
                return "Reservations cannot exceed 30 days.";
            }

            return null; // No error
        } catch (DateTimeParseException e) {
            return "Invalid date format. Please use YYYY-MM-DD format.";
        }
    }

    public static boolean isValidDateRange(String checkInStr, String checkOutStr) {
        return getDateRangeValidationMessage(checkInStr, checkOutStr) == null;
    }
} 