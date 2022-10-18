package com.review.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LinkUtil {
    public static int checkLinkStatus(String path) {
        try {
            URL u = new URL(path);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("GET");  //OR  huc.setRequestMethod ("HEAD");
            huc.connect();
            return huc.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return 404;
        }
    }
}
