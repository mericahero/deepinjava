package com.meric.deepinjava.application.fiuquote.quoteserver;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WSServerConfig {
    private int port;

    public static final WSServerConfig DEFAULT = new WSServerConfig(8080);
}
