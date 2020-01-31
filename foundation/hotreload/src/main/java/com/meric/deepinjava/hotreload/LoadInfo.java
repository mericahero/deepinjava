package com.meric.deepinjava.hotreload;

import lombok.Data;

@Data
public class LoadInfo {
    private MyClassLoader classLoader;
    private long loadTime;
    private BaseManager manager;

    public LoadInfo(MyClassLoader classLoader, long loadTime) {
        this.classLoader = classLoader;
        this.loadTime = loadTime;
    }
}
