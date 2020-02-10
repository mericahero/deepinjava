
package com.meric.deepinjava.application.fiuquote.fiucollect.protocol;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ConnectTurnOverProtocol {

    private String protocol;
    private String market;
    private String direction;
    private String time;
    private String buyturnover;
    private String sellturnover;
    private String buysell;

}