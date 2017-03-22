package com.tadpole.northmuse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tadpole.northmuse.domain.WebSiteUrl;
import com.tadpole.northmuse.service.WebSiteUrlService;
import com.tadpole.northmuse.web.rest.util.HeaderUtil;
import com.tadpole.northmuse.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing WebSiteUrl.
 */
@RestController
@RequestMapping("/api")
public class WebSiteUrlResource {

    private final Logger log = LoggerFactory.getLogger(WebSiteUrlResource.class);

    private static final String ENTITY_NAME = "webSiteUrl";
        
    private final WebSiteUrlService webSiteUrlService;

    public WebSiteUrlResource(WebSiteUrlService webSiteUrlService) {
        this.webSiteUrlService = webSiteUrlService;
    }

    /**
     * POST  /web-site-urls : Create a new webSiteUrl.
     *
     * @param webSiteUrl the webSiteUrl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new webSiteUrl, or with status 400 (Bad Request) if the webSiteUrl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/web-site-urls")
    @Timed
    public ResponseEntity<WebSiteUrl> createWebSiteUrl(@RequestBody WebSiteUrl webSiteUrl) throws URISyntaxException {
        log.debug("REST request to save WebSiteUrl : {}", webSiteUrl);
        if (webSiteUrl.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new webSiteUrl cannot already have an ID")).body(null);
        }
        WebSiteUrl result = webSiteUrlService.save(webSiteUrl);
        return ResponseEntity.created(new URI("/api/web-site-urls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /web-site-urls : Updates an existing webSiteUrl.
     *
     * @param webSiteUrl the webSiteUrl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated webSiteUrl,
     * or with status 400 (Bad Request) if the webSiteUrl is not valid,
     * or with status 500 (Internal Server Error) if the webSiteUrl couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/web-site-urls")
    @Timed
    public ResponseEntity<WebSiteUrl> updateWebSiteUrl(@RequestBody WebSiteUrl webSiteUrl) throws URISyntaxException {
        log.debug("REST request to update WebSiteUrl : {}", webSiteUrl);
        if (webSiteUrl.getId() == null) {
            return createWebSiteUrl(webSiteUrl);
        }
        WebSiteUrl result = webSiteUrlService.save(webSiteUrl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, webSiteUrl.getId().toString()))
            .body(result);
    }

    /**
     * GET  /web-site-urls : get all the webSiteUrls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of webSiteUrls in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/web-site-urls")
    @Timed
    public ResponseEntity<List<WebSiteUrl>> getAllWebSiteUrls(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WebSiteUrls");
        Page<WebSiteUrl> page = webSiteUrlService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/web-site-urls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /web-site-urls/:id : get the "id" webSiteUrl.
     *
     * @param id the id of the webSiteUrl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the webSiteUrl, or with status 404 (Not Found)
     */
    @GetMapping("/web-site-urls/{id}")
    @Timed
    public ResponseEntity<WebSiteUrl> getWebSiteUrl(@PathVariable Long id) {
        log.debug("REST request to get WebSiteUrl : {}", id);
        WebSiteUrl webSiteUrl = webSiteUrlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(webSiteUrl));
    }

    /**
     * DELETE  /web-site-urls/:id : delete the "id" webSiteUrl.
     *
     * @param id the id of the webSiteUrl to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/web-site-urls/{id}")
    @Timed
    public ResponseEntity<Void> deleteWebSiteUrl(@PathVariable Long id) {
        log.debug("REST request to delete WebSiteUrl : {}", id);
        webSiteUrlService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
