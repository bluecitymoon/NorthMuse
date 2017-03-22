package com.tadpole.northmuse.service.impl;

import com.tadpole.northmuse.service.UrlParameterService;
import com.tadpole.northmuse.domain.UrlParameter;
import com.tadpole.northmuse.repository.UrlParameterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing UrlParameter.
 */
@Service
@Transactional
public class UrlParameterServiceImpl implements UrlParameterService{

    private final Logger log = LoggerFactory.getLogger(UrlParameterServiceImpl.class);
    
    private final UrlParameterRepository urlParameterRepository;

    public UrlParameterServiceImpl(UrlParameterRepository urlParameterRepository) {
        this.urlParameterRepository = urlParameterRepository;
    }

    /**
     * Save a urlParameter.
     *
     * @param urlParameter the entity to save
     * @return the persisted entity
     */
    @Override
    public UrlParameter save(UrlParameter urlParameter) {
        log.debug("Request to save UrlParameter : {}", urlParameter);
        UrlParameter result = urlParameterRepository.save(urlParameter);
        return result;
    }

    /**
     *  Get all the urlParameters.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UrlParameter> findAll(Pageable pageable) {
        log.debug("Request to get all UrlParameters");
        Page<UrlParameter> result = urlParameterRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one urlParameter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UrlParameter findOne(Long id) {
        log.debug("Request to get UrlParameter : {}", id);
        UrlParameter urlParameter = urlParameterRepository.findOne(id);
        return urlParameter;
    }

    /**
     *  Delete the  urlParameter by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UrlParameter : {}", id);
        urlParameterRepository.delete(id);
    }
}
