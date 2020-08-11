package com.meric.deepinjava.springboot.mybatis;

import com.meric.deepinjava.springboot.mybatis.config.AppConfig;
import com.meric.deepinjava.springboot.mybatis.service.IA;
import com.meric.deepinjava.springboot.mybatis.service.IAImpl;
import com.meric.deepinjava.springboot.mybatis.service.TestService;
import lombok.val;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MybatisApplication {
    public static void main(String[] args) {
        val context = new AnnotationConfigApplicationContext(AppConfig.class);

        val bean = context.getBean(TestService.class);

        val a = context.getBean(IA.class);

        a.print1();

        bean.query();
        bean.query();
    }
}
