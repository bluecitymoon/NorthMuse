package com.tadpole.northmuse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tadpole.northmuse.domain.DouBanMovieTag;
import com.tadpole.northmuse.repository.DouBanMovieTagRepository;
import com.tadpole.northmuse.service.DouBanMovieTagService;
import com.tadpole.northmuse.vo.DouBanTags;
import com.tadpole.northmuse.web.rest.util.HeaderUtil;
import com.tadpole.northmuse.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DouBanMovieTag.
 */
@RestController
@RequestMapping("/api")
public class DouBanMovieTagResource {

    private final Logger log = LoggerFactory.getLogger(DouBanMovieTagResource.class);

    private static final String ENTITY_NAME = "douBanMovieTag";



    private final DouBanMovieTagService douBanMovieTagService;

    public DouBanMovieTagResource(DouBanMovieTagService douBanMovieTagService) {
        this.douBanMovieTagService = douBanMovieTagService;
    }

    /**
     * POST  /dou-ban-movie-tags : Create a new douBanMovieTag.
     *
     * @param douBanMovieTag the douBanMovieTag to create
     * @return the ResponseEntity with status 201 (Created) and with body the new douBanMovieTag, or with status 400 (Bad Request) if the douBanMovieTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dou-ban-movie-tags")
    @Timed
    public ResponseEntity<DouBanMovieTag> createDouBanMovieTag(@RequestBody DouBanMovieTag douBanMovieTag) throws URISyntaxException {
        log.debug("REST request to save DouBanMovieTag : {}", douBanMovieTag);
        if (douBanMovieTag.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new douBanMovieTag cannot already have an ID")).body(null);
        }
        DouBanMovieTag result = douBanMovieTagService.save(douBanMovieTag);
        return ResponseEntity.created(new URI("/api/dou-ban-movie-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/dou-ban-movie-tags/batch")
    @Timed
    public ResponseEntity<List<DouBanMovieTag>> refreshDouBanMovieTags(@RequestBody DouBanTags bag) throws URISyntaxException {

        douBanMovieTagService.deleteAll();

        List<DouBanMovieTag> tagList = new ArrayList<>();
        for (String tag: bag.getTags()) {

            DouBanMovieTag douBanMovieTag = DouBanMovieTag.builder().tag(tag).build();

            tagList.add(douBanMovieTag);
        }

        List<DouBanMovieTag> result = douBanMovieTagService.batchSave(tagList);

        return ResponseEntity.created(new URI("/api/dou-ban-movie-tags/batch"))
            .body(result);
    }


    /**
     * PUT  /dou-ban-movie-tags : Updates an existing douBanMovieTag.
     *
     * @param douBanMovieTag the douBanMovieTag to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated douBanMovieTag,
     * or with status 400 (Bad Request) if the douBanMovieTag is not valid,
     * or with status 500 (Internal Server Error) if the douBanMovieTag couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dou-ban-movie-tags")
    @Timed
    public ResponseEntity<DouBanMovieTag> updateDouBanMovieTag(@RequestBody DouBanMovieTag douBanMovieTag) throws URISyntaxException {
        log.debug("REST request to update DouBanMovieTag : {}", douBanMovieTag);
        if (douBanMovieTag.getId() == null) {
            return createDouBanMovieTag(douBanMovieTag);
        }
        DouBanMovieTag result = douBanMovieTagService.save(douBanMovieTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, douBanMovieTag.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dou-ban-movie-tags : get all the douBanMovieTags.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of douBanMovieTags in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/dou-ban-movie-tags")
    @Timed
    public ResponseEntity<List<DouBanMovieTag>> getAllDouBanMovieTags(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DouBanMovieTags");
        Page<DouBanMovieTag> page = douBanMovieTagService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dou-ban-movie-tags");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dou-ban-movie-tags/:id : get the "id" douBanMovieTag.
     *
     * @param id the id of the douBanMovieTag to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the douBanMovieTag, or with status 404 (Not Found)
     */
    @GetMapping("/dou-ban-movie-tags/{id}")
    @Timed
    public ResponseEntity<DouBanMovieTag> getDouBanMovieTag(@PathVariable Long id) {
        log.debug("REST request to get DouBanMovieTag : {}", id);
        DouBanMovieTag douBanMovieTag = douBanMovieTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(douBanMovieTag));
    }

    /**
     * DELETE  /dou-ban-movie-tags/:id : delete the "id" douBanMovieTag.
     *
     * @param id the id of the douBanMovieTag to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dou-ban-movie-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteDouBanMovieTag(@PathVariable Long id) {
        log.debug("REST request to delete DouBanMovieTag : {}", id);
        douBanMovieTagService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
