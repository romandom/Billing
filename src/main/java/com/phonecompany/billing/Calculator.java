package com.phonecompany.billing;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Calculator implements TelephoneBillCalculator {
    @Override
    public BigDecimal calculate(String phoneLog) throws ParseException {

        String[] split = phoneLog.split(",");

        String number = split[0];
        String from = split[1];
        String to = split[2];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateFrom = LocalDateTime.parse(from, formatter);
        LocalDateTime dateTo = LocalDateTime.parse(to, formatter);

        long diff = ChronoUnit.MINUTES.between(dateFrom, dateTo);

        if (diff != 0) {
            diff += 1;
        }

        if (diff == 0 && ChronoUnit.SECONDS.between(dateFrom, dateTo) != 0) {
            diff = 1;
        }

        if (diff <= 5) {
            if (dateFrom.getHour() == 7 && dateFrom.getMinute() + diff >= 60) {
                long temp = dateFrom.getMinute() + diff - 60;
                return BigDecimal.valueOf((diff - temp) * 0.5 + temp);
            }
            if (dateFrom.getHour() < 8 && dateFrom.getMinute() + diff < 60) {
                return BigDecimal.valueOf(diff * 0.5);
            }
            if (dateFrom.getHour() == 15 && dateFrom.getMinute() + diff >= 60) {
                long temp = dateFrom.getMinute() + diff - 60;
                return BigDecimal.valueOf((diff - temp) + temp * 0.5);
            }
            if (dateFrom.getHour() < 16 && dateFrom.getMinute() + diff < 60) {
                return BigDecimal.valueOf(diff);
            }
            return BigDecimal.valueOf(diff*0.5);
        }

        if (diff > 5) {
            if (dateFrom.getHour() == 7 && dateFrom.getMinute() + 5 >= 60) {
                long temp = dateFrom.getMinute() + 5 - 60;
                long minutesBeforeEight = 60 - dateFrom.getMinute();
                return BigDecimal.valueOf(minutesBeforeEight * 0.5 + temp + (diff - temp - minutesBeforeEight) * 0.2);
            }
            if (dateFrom.getHour() == 15 && dateFrom.getMinute() + 5 >= 60) {
                long temp = dateFrom.getMinute() + 5 - 60;
                long minutesBeforeSixteen = 60 - dateFrom.getMinute();
                return BigDecimal.valueOf(minutesBeforeSixteen + temp * 0.5 + (diff - temp - minutesBeforeSixteen) * 0.2);
            }
            if(dateFrom.getHour() < 16){
                return BigDecimal.valueOf(5 + (diff - 5) * 0.2);
            }
            return BigDecimal.valueOf(5*0.5 + (diff-5)*0.2);
        }

        return null;
    }

}
