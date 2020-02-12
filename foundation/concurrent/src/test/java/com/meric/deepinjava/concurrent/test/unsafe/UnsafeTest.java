package com.meric.deepinjava.concurrent.test.unsafe;

import com.meric.deepinjava.concurrent.ConcurrentLauncher;
import com.meric.deepinjava.concurrent.ConcurrentPredictor;

import com.meric.deepinjava.concurrent.unsafe.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class UnsafeTest {

    @Test
    public void stringBuilder() throws InterruptedException, ExecutionException {
        ConcurrentPredictor predictor = new StringBuilderPredictor();

        ConcurrentLauncher launcher = new ConcurrentLauncher(
                5000,
                200,
                predictor
        );
        launcher.start();
        Assert.assertTrue(launcher.getPredictResult());
    }


    @Test
    public void stringBuffer() throws InterruptedException, ExecutionException {
        ConcurrentPredictor predictor = new StringBufferPredictor();
        ConcurrentLauncher launcher = new ConcurrentLauncher(
                5000,
                200,
                predictor
        );
        launcher.start();
        Assert.assertTrue(launcher.getPredictResult());
    }

    @Test
    public void simpleDateFormat() throws ExecutionException, InterruptedException {
        ConcurrentLauncher launcher = new ConcurrentLauncher(
                50,
                20,
                new SimpleDateFormatPredictor()
        );
        launcher.start();
        Assert.assertTrue(launcher.getPredictResult());
    }

    @Test
    public void JodaDateFormatter() throws InterruptedException, ExecutionException {
        ConcurrentLauncher launcher = new ConcurrentLauncher(
                5000,
                20,
                new JodaTimeDateFormatPredictor()
        );
        launcher.start();
        Assert.assertTrue(launcher.getPredictResult());
    }

    @Test
    public void list() throws InterruptedException, ExecutionException {
        ConcurrentLauncher launcher = new ConcurrentLauncher(
                5000,
                20,
                new ListPredictor()
        );
        launcher.start();
        Assert.assertTrue(launcher.getPredictResult());
    }

    @Test
    public void set() throws InterruptedException, ExecutionException {
        ConcurrentLauncher launcher = new ConcurrentLauncher(
                5000,
                20,
                new SetPredictor()
        );
        launcher.start();
        Assert.assertTrue(launcher.getPredictResult());
    }


    @Test
    public void map() throws InterruptedException, ExecutionException {
        ConcurrentLauncher launcher = new ConcurrentLauncher(
                5000,
                20,
                new MapPredictor()
        );
        launcher.start();
        Assert.assertTrue(launcher.getPredictResult());
    }

}
