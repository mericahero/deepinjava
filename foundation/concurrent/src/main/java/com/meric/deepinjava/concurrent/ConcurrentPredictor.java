package com.meric.deepinjava.concurrent;

import java.text.ParseException;

public interface ConcurrentPredictor {
    void action() throws ParseException;
    int predict();
}
