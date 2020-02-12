package com.meric.deepinjava.concurrent.test.lock;

import com.meric.deepinjava.concurrent.ConcurrentLauncher;
import com.meric.deepinjava.concurrent.lock.ReentrantLockPredictor;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class LockTest {

    @Test
    public void reentrantLock() throws InterruptedException, ExecutionException {
        ConcurrentLauncher launcher = new ConcurrentLauncher(
                5000,
                20,
                new ReentrantLockPredictor()
        );
        launcher.start();
        Assert.assertTrue(launcher.getPredictResult());
    }
}
