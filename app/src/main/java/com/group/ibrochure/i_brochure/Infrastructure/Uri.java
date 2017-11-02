package com.group.ibrochure.i_brochure.Infrastructure;

/**
 * Created by Yogi on 02/11/2017.
 */

public class Uri {
    public static String sourceUri = "http://localhost:8080/iBrochure/api/";

    public static String getUri(String destUri) {
        return sourceUri + destUri;
    }
}
