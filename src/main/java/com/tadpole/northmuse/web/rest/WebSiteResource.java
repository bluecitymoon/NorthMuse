package com.tadpole.northmuse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tadpole.northmuse.domain.WebSite;
import com.tadpole.northmuse.service.WebSiteService;
import com.tadpole.northmuse.web.rest.util.HeaderUtil;
import com.tadpole.northmuse.web.rest.util.PaginationUtil;
import de.sstoehr.harreader.model.HarLog;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WebSite.
 */
@RestController
@RequestMapping("/api")
public class WebSiteResource {

    private final Logger log = LoggerFactory.getLogger(WebSiteResource.class);

    private static final String ENTITY_NAME = "webSite";

    private final WebSiteService webSiteService;


    public WebSiteResource(WebSiteService webSiteService) {
        this.webSiteService = webSiteService;
    }

    /**
     * POST  /web-sites : Create a new webSite.
     *
     * @param webSite the webSite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new webSite, or with status 400 (Bad Request) if the webSite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/web-sites")
    @Timed
    public ResponseEntity<WebSite> createWebSite(@RequestBody WebSite webSite) throws URISyntaxException {
        log.debug("REST request to save WebSite : {}", webSite);
        if (webSite.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new webSite cannot already have an ID")).body(null);
        }
        WebSite result = webSiteService.save(webSite);
        return ResponseEntity.created(new URI("/api/web-sites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /web-sites : Updates an existing webSite.
     *
     * @param webSite the webSite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated webSite,
     * or with status 400 (Bad Request) if the webSite is not valid,
     * or with status 500 (Internal Server Error) if the webSite couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/web-sites")
    @Timed
    public ResponseEntity<WebSite> updateWebSite(@RequestBody WebSite webSite) throws URISyntaxException {
        log.debug("REST request to update WebSite : {}", webSite);
        if (webSite.getId() == null) {
            return createWebSite(webSite);
        }
        WebSite result = webSiteService.save(webSite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, webSite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /web-sites : get all the webSites.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of webSites in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/web-sites")
    @Timed
    public ResponseEntity<List<WebSite>> getAllWebSites(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WebSites");
        Page<WebSite> page = webSiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/web-sites");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /web-sites/:id : get the "id" webSite.
     *
     * @param id the id of the webSite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the webSite, or with status 404 (Not Found)
     */
    @GetMapping("/web-sites/{id}")
    @Timed
    public ResponseEntity<WebSite> getWebSite(@PathVariable Long id) {
        log.debug("REST request to get WebSite : {}", id);
        WebSite webSite = webSiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(webSite));
    }


    /**
     * DELETE  /web-sites/:id : delete the "id" webSite.
     *
     * @param id the id of the webSite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/web-sites/{id}")
    @Timed
    public ResponseEntity<Void> deleteWebSite(@PathVariable Long id) {
        log.debug("REST request to delete WebSite : {}", id);
        webSiteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }



    @GetMapping("/web-sites/analysis/{id}")
    @Timed
    public ResponseEntity<HarLog> analysisWebSite(@PathVariable Long id) {
        log.debug("REST request to get WebSite : {}", id);
        WebSite webSite = webSiteService.findOne(id);

        HarLog log = webSiteService.analysis(webSite);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(log));
    }
}
