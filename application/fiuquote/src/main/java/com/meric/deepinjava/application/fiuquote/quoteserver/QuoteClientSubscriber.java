package com.meric.deepinjava.application.fiuquote.quoteserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import javafx.util.Pair;
import lombok.val;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class QuoteClientSubscriber {
    private static class QuoteClientSubscriberHolder {
        static QuoteClientSubscriber Instance = new QuoteClientSubscriber();
    }

    public static QuoteClientSubscriber getInstance(){
        return QuoteClientSubscriberHolder.Instance;
    }

    private ConcurrentHashMap<String, ChannelGroup> quoteChannelMap;

    private static final String[] AllCommands = {"addallstocks","removeallstock", "addstock","removestock","addtrade","removetrade","addorder","removeorder"};

    private QuoteClientSubscriber(){
        quoteChannelMap = new ConcurrentHashMap<>();
    }

    public boolean execCommand(String commandStr,ChannelHandlerContext ctx){
        val commandDetail = getCommand(commandStr);
        if(commandDetail==null){
            return false;
        }
        switch (commandDetail.getKey()){
            case "addallstocks":
                addStock(new String[]{"allstock"},ctx);
                break;
            case "addstock":
                addStock(commandDetail.getValue(),ctx);
                break;
            case "removestock":
                removeStock(commandDetail.getValue(),ctx);
                break;
            default:
                break;
        }
        return true;
    }

    public ChannelGroup getStockChannelGroup(String stock){
        ensureStockExistedInMap(stock);
        return quoteChannelMap.get(stock);
    }

    public boolean fireStock(String stock, Consumer<ChannelGroup> consumer){
        ensureStockExistedInMap(stock);
        ensureStockExistedInMap("allstock");
        val group = quoteChannelMap.get(stock);
        consumer.accept(group);
        val aGroup = quoteChannelMap.get("allstock");
        consumer.accept(aGroup);
        return true;
    }

    private void removeStock(String[] stocks, ChannelHandlerContext ctx) {
        if(stocks==null){
            return ;
        }
        for (String stock:stocks){
            ensureStockExistedInMap(stock);
            val ctxs = quoteChannelMap.get(stock);
            if(ctxs.contains(ctx)){
                ctxs.remove(ctx.channel());
            }
        }
    }

    private void addStock(String[] stocks,ChannelHandlerContext ctx){
        if(stocks==null){
            return ;
        }
        for (String stock:stocks){
            ensureStockExistedInMap(stock);
            val ctxs = quoteChannelMap.get(stock);
            ctxs.add(ctx.channel());
        }
    }



    private void ensureStockExistedInMap(String stock){
        if(!quoteChannelMap.containsKey(stock)){
            quoteChannelMap.put(stock,new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        }
    }

    public Pair<String,String[]> getCommand(String commandStr){
        if(StringUtils.isEmpty(commandStr)){
            return null;
        }
        val cmdArray = commandStr.split(" ");
        val command = cmdArray[0];
        if(!ArrayUtils.contains(AllCommands,command)){
            return null;
        }
        String[] commandArgs = null;
        if(cmdArray.length>1){
            commandArgs =  cmdArray[1].split(",");
        }

        return new Pair<>(command,commandArgs);
    }
}
