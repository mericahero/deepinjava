package com.meric.utility.ljson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 *
 * ObjectMixin
 *
 * @author: Meric chenchen327@jd.com
 * @create: 2020/08/12
 **/
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,include = JsonTypeInfo.As.PROPERTY,property = "_class")
public class ObjectMixin {
}