package com.tadpole.northmuse.service.impl;

import com.tadpole.northmuse.service.ParameterTypeService;
import com.tadpole.northmuse.domain.ParameterType;
import com.tadpole.northmuse.repository.ParameterTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing ParameterType.
 */
@Service
@Transactional
public class ParameterTypeServiceImpl implements ParameterTypeService{

    private final Logger log = LoggerFactory.getLogger(ParameterTypeServiceImpl.class);
    
    private final ParameterTypeRepository parameterTypeRepository;

    public ParameterTypeServiceImpl(ParameterTypeRepository parameterTypeRepository) {
        this.parameterTypeRepository = parameterTypeRepository;
    }

    /**
     * Save a parameterType.
     *
     * @param parameterType the entity to save
     * @return the persisted entity
     */
    @Override
    public ParameterType save(ParameterType parameterType) {
        log.debug("Request to save ParameterType : {}", parameterType);
        ParameterType result = parameterTypeRepository.save(parameterType);
        return result;
    }

    /**
     *  Get all the parameterTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParameterType> findAll(Pageable pageable) {
        log.debug("Request to get all ParameterTypes");
        Page<ParameterType> result = parameterTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one parameterType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ParameterType findOne(Long id) {
        log.debug("Request to get ParameterType : {}", id);
        ParameterType parameterType = parameterTypeRepository.findOne(id);
        return parameterType;
    }

    /**
     *  Delete the  parameterType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParameterType : {}", id);
        parameterTypeRepository.delete(id);
    }
}
