package com.tadpole.northmuse.service;

import com.tadpole.northmuse.domain.WebSiteUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing WebSiteUrl.
 */
public interface WebSiteUrlService {

    /**
     * Save a webSiteUrl.
     *
     * @param webSiteUrl the entity to save
     * @return the persisted entity
     */
    WebSiteUrl save(WebSiteUrl webSiteUrl);

    /**
     *  Get all the webSiteUrls.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WebSiteUrl> findAll(Pageable pageable);

    /**
     *  Get the "id" webSiteUrl.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WebSiteUrl findOne(Long id);

    /**
     *  Delete the "id" webSiteUrl.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
