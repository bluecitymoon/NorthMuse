package com.tadpole.northmuse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tadpole.northmuse.domain.ParameterType;
import com.tadpole.northmuse.service.ParameterTypeService;
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
 * REST controller for managing ParameterType.
 */
@RestController
@RequestMapping("/api")
public class ParameterTypeResource {

    private final Logger log = LoggerFactory.getLogger(ParameterTypeResource.class);

    private static final String ENTITY_NAME = "parameterType";
        
    private final ParameterTypeService parameterTypeService;

    public ParameterTypeResource(ParameterTypeService parameterTypeService) {
        this.parameterTypeService = parameterTypeService;
    }

    /**
     * POST  /parameter-types : Create a new parameterType.
     *
     * @param parameterType the parameterType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parameterType, or with status 400 (Bad Request) if the parameterType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parameter-types")
    @Timed
    public ResponseEntity<ParameterType> createParameterType(@RequestBody ParameterType parameterType) throws URISyntaxException {
        log.debug("REST request to save ParameterType : {}", parameterType);
        if (parameterType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new parameterType cannot already have an ID")).body(null);
        }
        ParameterType result = parameterTypeService.save(parameterType);
        return ResponseEntity.created(new URI("/api/parameter-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parameter-types : Updates an existing parameterType.
     *
     * @param parameterType the parameterType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parameterType,
     * or with status 400 (Bad Request) if the parameterType is not valid,
     * or with status 500 (Internal Server Error) if the parameterType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parameter-types")
    @Timed
    public ResponseEntity<ParameterType> updateParameterType(@RequestBody ParameterType parameterType) throws URISyntaxException {
        log.debug("REST request to update ParameterType : {}", parameterType);
        if (parameterType.getId() == null) {
            return createParameterType(parameterType);
        }
        ParameterType result = parameterTypeService.save(parameterType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parameterType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parameter-types : get all the parameterTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parameterTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/parameter-types")
    @Timed
    public ResponseEntity<List<ParameterType>> getAllParameterTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ParameterTypes");
        Page<ParameterType> page = parameterTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parameter-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parameter-types/:id : get the "id" parameterType.
     *
     * @param id the id of the parameterType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parameterType, or with status 404 (Not Found)
     */
    @GetMapping("/parameter-types/{id}")
    @Timed
    public ResponseEntity<ParameterType> getParameterType(@PathVariable Long id) {
        log.debug("REST request to get ParameterType : {}", id);
        ParameterType parameterType = parameterTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parameterType));
    }

    /**
     * DELETE  /parameter-types/:id : delete the "id" parameterType.
     *
     * @param id the id of the parameterType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parameter-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteParameterType(@PathVariable Long id) {
        log.debug("REST request to delete ParameterType : {}", id);
        parameterTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
