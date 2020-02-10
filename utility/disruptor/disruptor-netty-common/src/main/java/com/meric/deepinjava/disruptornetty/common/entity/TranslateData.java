package com.meric.deepinjava.disruptornetty.common.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
public class TranslateData implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String message;
}
