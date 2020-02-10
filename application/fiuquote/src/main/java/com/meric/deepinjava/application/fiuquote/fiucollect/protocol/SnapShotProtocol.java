
package com.meric.deepinjava.application.fiuquote.fiucollect.protocol;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SnapShotProtocol {

    private String protocol;
    private String code;
    private String name;
    private String time;
    private String preclose;
    private String open;
    private String high;
    private String low;
    private String close;
    private String last;
    private String vwap;
    private String sharestraded;
    private String turnover;
    private String shortshares;
    private String shortturnover;

}