package com.meric.deepinjava.concurrent.unsafe;

import com.meric.deepinjava.concurrent.ConcurrentPredictor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;

public class JodaTimeDateFormatPredictor implements ConcurrentPredictor {
    private DateTimeFormatter formater = DateTimeFormat.forPattern("yyyy-MM-dd");
    @Override
    public void action() throws ParseException {
        formater.parseDateTime("2019-01-05");
        DateTime.parse("2019-01-05",formater).toDate();
    }

    @Override
    public int predict() {
        return 5000;
    }
}
