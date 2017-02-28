package com.tadpole.northmuse.service;

import com.tadpole.northmuse.domain.WebSite;
import com.tadpole.northmuse.vo.AnalysisResponse;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing WebSite.
 */
public interface WebSiteService {

    /**
     * Save a webSite.
     *
     * @param webSite the entity to save
     * @return the persisted entity
     */
    WebSite save(WebSite webSite);

    /**
     *  Get all the webSites.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WebSite> findAll(Pageable pageable);

    /**
     *  Get the "id" webSite.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WebSite findOne(Long id);

    /**
     *  Delete the "id" webSite.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    AnalysisResponse analysis(WebSite webSite);

}
