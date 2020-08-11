package com.meric.deepinjava.application.fiuquote;

import com.meric.deepinjava.application.fiuquote.fiucollect.FiuClient;
import com.meric.deepinjava.application.fiuquote.fiucollect.FiuConfigration;
import com.meric.deepinjava.application.fiuquote.fiucollect.disruptor.FiuDataRingBufferWorker;
import com.meric.deepinjava.application.fiuquote.fiucollect.disruptor.QuoteDataRingBufferWorker;
import com.meric.deepinjava.application.fiuquote.quoteserver.WSServer;
import com.meric.deepinjava.application.fiuquote.quoteserver.WSServerConfig;
import lombok.val;

import java.io.IOException;

public class QuoteBootstrap {
    public static void main(String[] args) throws InterruptedException, IOException {



        FiuDataRingBufferWorker.getInstance().start(5);
        QuoteDataRingBufferWorker.getInstance().start(5);



        new Thread(()->{
            val wsserver = new WSServer(WSServerConfig.DEFAULT);
            wsserver.start();
        }).start();


        val fThread = new Thread(()->{
            System.out.println("starting fiuclient...");
            val fiuClient = new FiuClient(FiuConfigration.USConfig);
            try {
                fiuClient.start(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fiuClient.sayHello();
        });

        fThread.start();

    }
}
