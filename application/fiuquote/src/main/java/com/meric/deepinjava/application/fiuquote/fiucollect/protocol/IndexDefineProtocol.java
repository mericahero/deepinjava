
package com.meric.deepinjava.application.fiuquote.fiucollect.protocol;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IndexDefineProtocol {

    private String protocol;
    private String code;
    private String indexsource;
    private String currency;

}