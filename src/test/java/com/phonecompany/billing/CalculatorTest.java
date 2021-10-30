package com.phonecompany.billing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;


class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    void calculateTroughIntervals() throws ParseException {
        String phoneLog = "420774577453,13-01-2020 15:56:15,13-01-2020 16:01:15";
        Assertions.assertEquals(calculator.calculate(phoneLog), BigDecimal.valueOf(4.7));
    }

    @Test
    void calculateOutOfIntervalMoreMinutes() throws ParseException {
        String phoneLog = "420774577453,13-01-2020 08:50:15,13-01-2020 08:56:15";
        Assertions.assertEquals(calculator.calculate(phoneLog), BigDecimal.valueOf(5.4));
    }

    @Test
    void calculateOutOfIntervalLessMinutes() throws ParseException {
        String phoneLog = "420774577453,13-01-2020 08:50:15,13-01-2020 08:54:15";
        Assertions.assertEquals(calculator.calculate(phoneLog), BigDecimal.valueOf(5));
    }

    @Test
    void calculateTroughTwoIntervals() throws ParseException {
        String phoneLog = "420774577453,13-01-2020 08:57:15,13-01-2020 09:01:15";
        Assertions.assertEquals(calculator.calculate(phoneLog), BigDecimal.valueOf(2.5));
    }
}