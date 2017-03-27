package com.tadpole.northmuse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tadpole.northmuse.domain.WebService;
import com.tadpole.northmuse.service.WebServiceService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WebService.
 */
@RestController
@RequestMapping("/api")
public class WebServiceResource {

    private final Logger log = LoggerFactory.getLogger(WebServiceResource.class);

    private static final String ENTITY_NAME = "webService";
        
    private final WebServiceService webServiceService;

    public WebServiceResource(WebServiceService webServiceService) {
        this.webServiceService = webServiceService;
    }

    /**
     * POST  /web-services : Create a new webService.
     *
     * @param webService the webService to create
     * @return the ResponseEntity with status 201 (Created) and with body the new webService, or with status 400 (Bad Request) if the webService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/web-services")
    @Timed
    public ResponseEntity<WebService> createWebService(@Valid @RequestBody WebService webService) throws URISyntaxException {
        log.debug("REST request to save WebService : {}", webService);
        if (webService.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new webService cannot already have an ID")).body(null);
        }
        WebService result = webServiceService.save(webService);
        return ResponseEntity.created(new URI("/api/web-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /web-services : Updates an existing webService.
     *
     * @param webService the webService to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated webService,
     * or with status 400 (Bad Request) if the webService is not valid,
     * or with status 500 (Internal Server Error) if the webService couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/web-services")
    @Timed
    public ResponseEntity<WebService> updateWebService(@Valid @RequestBody WebService webService) throws URISyntaxException {
        log.debug("REST request to update WebService : {}", webService);
        if (webService.getId() == null) {
            return createWebService(webService);
        }
        WebService result = webServiceService.save(webService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, webService.getId().toString()))
            .body(result);
    }

    /**
     * GET  /web-services : get all the webServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of webServices in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/web-services")
    @Timed
    public ResponseEntity<List<WebService>> getAllWebServices(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WebServices");
        Page<WebService> page = webServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/web-services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /web-services/:id : get the "id" webService.
     *
     * @param id the id of the webService to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the webService, or with status 404 (Not Found)
     */
    @GetMapping("/web-services/{id}")
    @Timed
    public ResponseEntity<WebService> getWebService(@PathVariable Long id) {
        log.debug("REST request to get WebService : {}", id);
        WebService webService = webServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(webService));
    }

    /**
     * DELETE  /web-services/:id : delete the "id" webService.
     *
     * @param id the id of the webService to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/web-services/{id}")
    @Timed
    public ResponseEntity<Void> deleteWebService(@PathVariable Long id) {
        log.debug("REST request to delete WebService : {}", id);
        webServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
