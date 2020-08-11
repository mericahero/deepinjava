package com.meric.deepinjava.springboot.mybatis.service;

import com.meric.deepinjava.springboot.mybatis.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestMapper testMapper;


    @Autowired
    private AnotherService anotherService;

    @Lookup
    protected AnotherService getAnotherService(){
        return null;
    }

    public void query(){
        testMapper.query();
        System.out.println(anotherService.a);
        System.out.println(anotherService.a);
        System.out.println(getAnotherService().a);
        System.out.println(getAnotherService().a);
        System.out.println(getAnotherService().a);
    }
}
