package com.meric.deepinjava.resume.collection;

import lombok.val;
import lombok.var;

import java.util.LinkedHashMap;
import java.util.Map;

public class XLRUCache<K,V> extends LinkedHashMap<K,V> {

    public int MAXENTITIES=10;

    public XLRUCache(boolean accessOrder){
        super(16,0.75f,accessOrder);
    }

    public XLRUCache(int max,boolean accessOrder){
        this(accessOrder);
        MAXENTITIES=max;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size()>MAXENTITIES;
    }

    public static void main(String[] args) {
        val cache = new XLRUCache<String,Integer>(5,true);
        for(int i=0;i<5;i++){
            cache.put( String.valueOf((char)((int)'a'+i)),i );
        }
        var result = cache.get("a");
        cache.put("f",6);
        result = cache.get("b");



     }
}
