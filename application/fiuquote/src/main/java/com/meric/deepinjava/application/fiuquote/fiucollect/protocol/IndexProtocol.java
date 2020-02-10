
package com.meric.deepinjava.application.fiuquote.fiucollect.protocol;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IndexProtocol {

    private String protocol;
    private String code;
    private String time;
    private String indexstatus;
    private String preclose;
    private String open;
    private String high;
    private String low;
    private String close;
    private String last;
    private String totalvol;
    private String turnover;
    private String netchgpreday;
    private String netchgpredaypct;
    private String easvalue;
}