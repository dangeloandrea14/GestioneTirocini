/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.utility;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author carlo
 */
public class DateUtils {

    public static Integer monthsBetweenIgnoreDays(Date start, Date end) {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(start);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(end);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        return diffMonth;
    }

    public static Integer getDay(Date date) {
        LocalDate localDate = date.toLocalDate();
        return localDate.getDayOfMonth();
    }

    public static Integer getMonth(Date date) {
        LocalDate localDate = date.toLocalDate();
        return localDate.getMonthValue();
    }

    public static Integer getYear(Date date) {
        LocalDate localDate = date.toLocalDate();
        return localDate.getYear();
    }
}
