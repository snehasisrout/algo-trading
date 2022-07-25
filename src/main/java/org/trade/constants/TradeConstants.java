package org.trade.constants;

import java.io.UnsupportedEncodingException;

public class TradeConstants {
    private TradeConstants(){}

    public static final String RUPEE = "\u20B9";
    public static final String UTF8 = "UTF-8";
    public static byte[] UTF8_BYTES;

    static {
        try {
            UTF8_BYTES = RUPEE.getBytes(UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
