package com.review.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataTimeUtil {
    private static LocalDate localDate = LocalDate.now();//For reference
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static String dateCurrentDDMMYYYY;

    public static String getDateCurrentDDMMYYYY() {
        if (dateCurrentDDMMYYYY == null) {
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateCurrentDDMMYYYY = localDate.format(formatter);
        }

        return dateCurrentDDMMYYYY;
    }
}
