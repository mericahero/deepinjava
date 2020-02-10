
package com.meric.deepinjava.application.fiuquote.fiucollect.protocol;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderBrokerProtocol {

    private String protocol;
    private String code;
    private String detail;
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public String getProtocol() {
        return protocol;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getDetail() {
        return detail;
    }

}