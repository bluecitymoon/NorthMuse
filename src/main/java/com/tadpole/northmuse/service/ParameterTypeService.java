package com.tadpole.northmuse.service;

import com.tadpole.northmuse.domain.ParameterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ParameterType.
 */
public interface ParameterTypeService {

    /**
     * Save a parameterType.
     *
     * @param parameterType the entity to save
     * @return the persisted entity
     */
    ParameterType save(ParameterType parameterType);

    /**
     *  Get all the parameterTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ParameterType> findAll(Pageable pageable);

    /**
     *  Get the "id" parameterType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ParameterType findOne(Long id);

    /**
     *  Delete the "id" parameterType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
