package com.meric.utility.ljson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sun.istack.internal.Nullable;
import lombok.var;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/** 
 * Application 
 * @author: Meric chenchen327@jd.com
 * @create: 2020/08/12
 **/
public class Application {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addSerializer(new NullValueSerializer(Object.class)));
        mapper.activateDefaultTypingAsProperty(LaissezFaireSubTypeValidator.instance
                ,
                ObjectMapper.DefaultTyping.NON_FINAL,"_class");
        var person = new Person().setAge(10).setName("person");
        String s = mapper.writeValueAsString(person);
        System.out.println(s);
        Object o = mapper.readValue(s, Object.class);
        System.in.read();


    }

    private static class NullValueSerializer extends StdSerializer<Object> {

        private static final long serialVersionUID = 1999052150548658808L;
        private String classIdentifier="_class";




        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeStringField(classIdentifier, value.getClass().getName());
            gen.writeEndObject();
        }

    }
}
