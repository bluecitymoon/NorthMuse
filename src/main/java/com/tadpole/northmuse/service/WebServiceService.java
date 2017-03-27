package com.tadpole.northmuse.service;

import com.tadpole.northmuse.domain.WebService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing WebService.
 */
public interface WebServiceService {

    /**
     * Save a webService.
     *
     * @param webService the entity to save
     * @return the persisted entity
     */
    WebService save(WebService webService);

    /**
     *  Get all the webServices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WebService> findAll(Pageable pageable);

    /**
     *  Get the "id" webService.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WebService findOne(Long id);

    /**
     *  Delete the "id" webService.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
