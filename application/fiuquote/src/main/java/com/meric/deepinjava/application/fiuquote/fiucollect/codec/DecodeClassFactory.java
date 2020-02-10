package com.meric.deepinjava.application.fiuquote.fiucollect.codec;

import com.meric.deepinjava.application.fiuquote.fiucollect.protocol.*;

import java.util.HashMap;
import java.util.Map;

public class DecodeClassFactory {
    private static Map<String,Class> protocolClassMap;

    static {
        protocolClassMap = new HashMap<>();
        protocolClassMap.put("stockdefine", StockDefineProtocol.class);
        protocolClassMap.put("securitystatus", SecurityStatusProtocol.class);
        protocolClassMap.put("snapshot", SnapShotProtocol.class);
        protocolClassMap.put("trade", TradeProtocol.class);
        protocolClassMap.put("order", OrderProtocol.class);
        protocolClassMap.put("orderbroker", OrderBrokerProtocol.class);
        protocolClassMap.put("connectbalance",ConnectBalanceProtocol.class);
        protocolClassMap.put("connectturnover",ConnectTurnOverProtocol.class);
        protocolClassMap.put("vcmtrigger",VcmTriggerProtocol.class);
        protocolClassMap.put("indexdefine",IndexDefineProtocol.class);
        protocolClassMap.put("index",IndexProtocol.class);
        protocolClassMap.put("heartbeat",BaseProtocol.class);
    }

    public static final DecodeClassFactory DEFAULT = new DecodeClassFactory();

    public Class<?> getClass(String protocol){
        return protocolClassMap.getOrDefault(protocol,BaseProtocol.class);
    }
}
