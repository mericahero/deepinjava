package com.meric.deepinjava.springboot.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IBImpl implements IB {
    @Autowired
    IA a ;



    @Override
    public void print1() {
        System.out.println("b print 1");
        a.print2();
    }

    @Override
    public void print2() {
        System.out.println("b print 2");
    }
}
