package com.meric.utility.ljson;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * Person
 *
 * @author: Meric chenchen327@jd.com
 * @create: 2020/08/12
 **/
@Data
@Accessors(chain = true)
public class Person {
    private String name;
    private Integer age;
}