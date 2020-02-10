package com.meric.deepinjava.application.fiuquote.fiucollect.protocol;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OrderProtocol {

    private String protocol;
    private String time;
    private String code;
    private List<OrderItem> bidlist;
    private List<OrderItem> asklist;


    @Data
    public class OrderItem {

        private String price;
        private String qty;
        private String num;

    }
}