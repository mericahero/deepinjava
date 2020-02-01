package com.meric.deepinjava.ldesruptor.examples.event;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class UserEvent {
    private int id;
    private String name;
    private int age;
    private List<String> handlerChain;
    private int producer;



    public UserEvent(int i){
        handlerChain =new ArrayList<>(8);
        id = i;
    }

    public void addChain(String c){
        handlerChain.add(c);
    }

    public String toString(){
        //return id+" --> "+  "[name] "+ name + " [age] "+ age +" [chain] "+String.join(",",chain);
        return id+"  -->  [producer] "+getProducer() +" [handlers] "+ String.join(",", handlerChain);
    }

}
