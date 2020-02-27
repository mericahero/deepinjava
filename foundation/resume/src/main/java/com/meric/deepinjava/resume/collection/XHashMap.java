package com.meric.deepinjava.resume.collection;

import lombok.val;

import java.util.HashMap;
import java.util.Map;

public class XHashMap {
    public static void main(String[] args) {
        val map = new HashMap<XMapKey,Integer>(6);
        map.put(new XMapKey(1),1);
        map.put(new XMapKey(2),2);
        map.put(new XMapKey(3),3);
        map.put(new XMapKey(4),4);
        map.put(new XMapKey(5),5);
        map.put(new XMapKey(6),1);
        map.put(new XMapKey(11),1);
        map.put(new XMapKey(16),1);
        val iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            val entry = iterator.next();
            System.out.println(entry.getKey()+":"+entry.getValue());
        }

    }

    public static class XMapKey {

        private int code;

        public XMapKey(int c){
            code = c;
        }


        @Override
        public int hashCode() {
            return code%5;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }
    }
}
