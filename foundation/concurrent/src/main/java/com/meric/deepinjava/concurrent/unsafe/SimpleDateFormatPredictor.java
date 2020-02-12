package com.meric.deepinjava.concurrent.unsafe;

import com.meric.deepinjava.concurrent.ConcurrentPredictor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatPredictor implements ConcurrentPredictor {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void action() throws ParseException {
        Date parse = format.parse("2019-10-10");
    }

    @Override
    public int predict() {
        return 0;
    }
}
