package com.meric.utility.ljson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
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

        // fail on ignored properties -> false
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);
        // fail on unknown properties  -> false
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES,false);
        mapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS,false);

        SimpleModule model = new SimpleModule();
        // model.addSerializer(Object.class,new NullValueSerializer(null));
        mapper.registerModule(model);
        mapper.activateDefaultTypingAsProperty(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                "_class");

        var person = new Person().setAge(10).setName("person");
        person = null;
        String s = mapper.writeValueAsString(person);
        Person  p = (Person)null;
        System.out.println(s);
        Object o = mapper.readValue(s, Object.class);
        System.in.read();


    }
    private static class NullValueSerializer extends StdSerializer<Object> {

        private static final long serialVersionUID = 1999052150548658808L;
        private final String classIdentifier;

        /**
         * @param classIdentifier can be {@literal null} and will be defaulted to {@code @class}.
         */
        NullValueSerializer(@Nullable String classIdentifier) {

            super(Object.class);
            this.classIdentifier = StringUtils.isNotEmpty(classIdentifier) ? classIdentifier : "_class";
        }

        /*
         * (non-Javadoc)
         * @see com.fasterxml.jackson.databind.ser.std.StdSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
         */
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException {

            jgen.writeStartObject();
            jgen.writeStringField(classIdentifier, Object.class.getName());
            jgen.writeEndObject();
        }
    }
}
