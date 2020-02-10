
package com.meric.deepinjava.application.fiuquote.fiucollect.protocol;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StockDefineProtocol {

    private String protocol;
    private String code;
    private String name;
    private String shortname;
    private String marketcode;
    private String currency;
    private String isincode;
    private String instrument;
    private String producttype;
    private String spread;
    private String lotsize;
    private String preclose;
    private String vcmflag;
    private String shortsell;
    private String casflag;
    private String ccassflag;
    private String dummyflag;
    private String  testflag;
    private String stampdutyflag;
    private String listingdate;
    private String delistingdate;
    private String freetext;
    private String enfflag;
    private String accured;
    private String couponrate;
    private String counversionratio;
    private String strikeprice1;
    private String strikeprice2;
    private String maturitydate;
    private String callput;
    private String style;
    private String warrenttype;
    private String callprice;
    private String decimalprice;
    private String entitlement;
    private String decimalentitlement;
    private String nowarrants;
    private String nounderlying;


}