
package com.meric.deepinjava.application.fiuquote.fiucollect.protocol;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TradeProtocol {

    private String protocol;
    private String code;
    private String name;
    private String time;
    private String tickerid;
    private String price;
    private String qty;
    private String type;
    private String cancelflag;
    private String direction;

}