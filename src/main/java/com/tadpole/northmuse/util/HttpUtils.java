package com.tadpole.northmuse.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * Created by Jerry on 2017/3/27.
 */
public class HttpUtils {

    public static WebClient newWebClient() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(15000);
        webClient.getOptions().setJavaScriptEnabled(false);

        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        return webClient;
    }
}


