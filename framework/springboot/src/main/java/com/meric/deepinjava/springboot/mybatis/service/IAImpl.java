package com.meric.deepinjava.springboot.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IAImpl implements IA {


     public IB b;

private IAImpl(){

}

    public IAImpl(@Autowired IB b){
        this.b=b;
    }

    @Override
    public void print1() {
        System.out.println("a print 1");
        b.print1();
    }

    @Override
    public void print2() {
        System.out.println("a print 2");
    }
}
