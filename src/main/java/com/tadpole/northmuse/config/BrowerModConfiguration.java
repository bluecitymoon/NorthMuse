package com.tadpole.northmuse.config;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Jerry on 2017/2/27.
 */
@Configuration
public class BrowerModConfiguration {

    @Bean(name = "browerMob")
    public BrowserMobProxyServer createBrowerMobProxyServer() {

        BrowserMobProxyServer proxy = new BrowserMobProxyServer();
        proxy.start(0);

        return proxy;
    }
}
