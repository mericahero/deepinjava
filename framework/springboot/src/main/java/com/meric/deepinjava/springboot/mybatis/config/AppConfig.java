package com.meric.deepinjava.springboot.mybatis.config;

import com.meric.deepinjava.springboot.mybatis.session.SessionFactoryBeanImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@Import(SessionFactoryBeanImportBeanDefinitionRegistrar.class)
@ComponentScan("com.meric.deepinjava.springboot.mybatis.service")
public class AppConfig {

}
