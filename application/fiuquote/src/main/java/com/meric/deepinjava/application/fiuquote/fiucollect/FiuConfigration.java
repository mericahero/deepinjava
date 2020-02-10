package com.meric.deepinjava.application.fiuquote.fiucollect;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FiuConfigration {
    private String host;
    private int port;

    public static final FiuConfigration USConfig
            = new FiuConfigration("10.0.19.85", 10001);

    public static final FiuConfigration HKConfig
            = new FiuConfigration("10.0.14.4", 10002);
}
