package com.tadpole.northmuse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tadpole.northmuse.domain.UrlParameter;
import com.tadpole.northmuse.service.UrlParameterService;
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
 * REST controller for managing UrlParameter.
 */
@RestController
@RequestMapping("/api")
public class UrlParameterResource {

    private final Logger log = LoggerFactory.getLogger(UrlParameterResource.class);

    private static final String ENTITY_NAME = "urlParameter";
        
    private final UrlParameterService urlParameterService;

    public UrlParameterResource(UrlParameterService urlParameterService) {
        this.urlParameterService = urlParameterService;
    }

    /**
     * POST  /url-parameters : Create a new urlParameter.
     *
     * @param urlParameter the urlParameter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new urlParameter, or with status 400 (Bad Request) if the urlParameter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/url-parameters")
    @Timed
    public ResponseEntity<UrlParameter> createUrlParameter(@RequestBody UrlParameter urlParameter) throws URISyntaxException {
        log.debug("REST request to save UrlParameter : {}", urlParameter);
        if (urlParameter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new urlParameter cannot already have an ID")).body(null);
        }
        UrlParameter result = urlParameterService.save(urlParameter);
        return ResponseEntity.created(new URI("/api/url-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /url-parameters : Updates an existing urlParameter.
     *
     * @param urlParameter the urlParameter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated urlParameter,
     * or with status 400 (Bad Request) if the urlParameter is not valid,
     * or with status 500 (Internal Server Error) if the urlParameter couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/url-parameters")
    @Timed
    public ResponseEntity<UrlParameter> updateUrlParameter(@RequestBody UrlParameter urlParameter) throws URISyntaxException {
        log.debug("REST request to update UrlParameter : {}", urlParameter);
        if (urlParameter.getId() == null) {
            return createUrlParameter(urlParameter);
        }
        UrlParameter result = urlParameterService.save(urlParameter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, urlParameter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /url-parameters : get all the urlParameters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of urlParameters in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/url-parameters")
    @Timed
    public ResponseEntity<List<UrlParameter>> getAllUrlParameters(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UrlParameters");
        Page<UrlParameter> page = urlParameterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/url-parameters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /url-parameters/:id : get the "id" urlParameter.
     *
     * @param id the id of the urlParameter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the urlParameter, or with status 404 (Not Found)
     */
    @GetMapping("/url-parameters/{id}")
    @Timed
    public ResponseEntity<UrlParameter> getUrlParameter(@PathVariable Long id) {
        log.debug("REST request to get UrlParameter : {}", id);
        UrlParameter urlParameter = urlParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(urlParameter));
    }

    /**
     * DELETE  /url-parameters/:id : delete the "id" urlParameter.
     *
     * @param id the id of the urlParameter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/url-parameters/{id}")
    @Timed
    public ResponseEntity<Void> deleteUrlParameter(@PathVariable Long id) {
        log.debug("REST request to delete UrlParameter : {}", id);
        urlParameterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
