package com.meric.deepinjava.springboot.mybatis.session;

import com.meric.deepinjava.springboot.mybatis.mapper.TestMapper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class SessionFactoryBeanImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder =  BeanDefinitionBuilder.genericBeanDefinition(SessionFactoryBean.class);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(TestMapper.class);
        registry.registerBeanDefinition("testMapper", beanDefinition);
    }
}
