package com.tadpole.northmuse.service.impl;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tadpole.northmuse.service.WebSiteService;
import com.tadpole.northmuse.domain.WebSite;
import com.tadpole.northmuse.repository.WebSiteRepository;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.proxy.CaptureType;
import org.jsoup.Jsoup;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Service Implementation for managing WebSite.
 */
@Service
@Transactional
public class WebSiteServiceImpl implements WebSiteService{

    private final Logger log = LoggerFactory.getLogger(WebSiteServiceImpl.class);

    private final WebSiteRepository webSiteRepository;

    @Autowired
    private BrowserMobProxyServer browserMobProxyServer;

    public WebSiteServiceImpl(WebSiteRepository webSiteRepository) {
        this.webSiteRepository = webSiteRepository;
    }

    /**
     * Save a webSite.
     *
     * @param webSite the entity to save
     * @return the persisted entity
     */
    @Override
    public WebSite save(WebSite webSite) {
        log.debug("Request to save WebSite : {}", webSite);
        WebSite result = webSiteRepository.save(webSite);
        return result;
    }

    /**
     *  Get all the webSites.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebSite> findAll(Pageable pageable) {
        log.debug("Request to get all WebSites");
        Page<WebSite> result = webSiteRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one webSite by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WebSite findOne(Long id) {
        log.debug("Request to get WebSite : {}", id);
        WebSite webSite = webSiteRepository.findOne(id);
        return webSite;
    }

    /**
     *  Delete the  webSite by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebSite : {}", id);
        webSiteRepository.delete(id);
    }

    @Override
    public de.sstoehr.harreader.model.HarLog analysis(WebSite webSite) {

        // TODO: 2017/2/27 not analysis in dev
        /*
        String url = webSite.getRootUrl();

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(browserMobProxyServer);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        WebDriver driver = new ChromeDriver(capabilities);

        browserMobProxyServer.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        browserMobProxyServer.newHar(url);

        driver.get(url);

        Har har = browserMobProxyServer.getHar();
*/
       // String harFileName = UUID.randomUUID().toString() + webSite.getId() + ".har";
        String harFileName = "a33a80fe-0f8c-4b02-b5c7-ab985beb04a91.har";

        /*
        try {
            har.writeTo(new File(harFileName));

        } catch (IOException e) {
            // TODO: 2017/2/27
        }

*/
        HarReader harReader = new HarReader();

        de.sstoehr.harreader.model.Har harResult = null;
        try {
            harResult = harReader.readFromFile(new File(harFileName));
        } catch (HarReaderException e) {
            // TODO: 2017/2/27
        }

        return harResult.getLog();
    }
}
