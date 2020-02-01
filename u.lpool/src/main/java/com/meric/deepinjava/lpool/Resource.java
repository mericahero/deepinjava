package com.meric.deepinjava.lpool;

import lombok.Data;
import lombok.ToString;

import javax.sound.midi.Soundbank;

@Data
public class Resource {
    private volatile static int gid;
    private int id;

    public Resource(){
        this.id = gid++;
        System.out.println("id: "+id);
    }

    @Override
    public String toString(){
        return "id: "+id;
    }

}
