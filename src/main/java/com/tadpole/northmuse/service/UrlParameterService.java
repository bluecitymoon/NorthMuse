package com.tadpole.northmuse.service;

import com.tadpole.northmuse.domain.UrlParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing UrlParameter.
 */
public interface UrlParameterService {

    /**
     * Save a urlParameter.
     *
     * @param urlParameter the entity to save
     * @return the persisted entity
     */
    UrlParameter save(UrlParameter urlParameter);

    /**
     *  Get all the urlParameters.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UrlParameter> findAll(Pageable pageable);

    /**
     *  Get the "id" urlParameter.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UrlParameter findOne(Long id);

    /**
     *  Delete the "id" urlParameter.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
