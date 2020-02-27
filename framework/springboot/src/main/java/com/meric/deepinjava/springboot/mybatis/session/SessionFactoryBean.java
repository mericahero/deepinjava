package com.meric.deepinjava.springboot.mybatis.session;

import org.springframework.beans.factory.FactoryBean;

public class SessionFactoryBean implements FactoryBean {

    private Class interfaceClazz;

    public SessionFactoryBean(Class clazz){
        this.interfaceClazz = clazz;
    }

    @Override
    public Object getObject() throws Exception {
        return new TestSession().getMapper(interfaceClazz);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClazz;
    }
}
