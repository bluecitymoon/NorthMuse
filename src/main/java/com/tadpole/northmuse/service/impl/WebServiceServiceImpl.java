package com.tadpole.northmuse.service.impl;

import com.tadpole.northmuse.service.WebServiceService;
import com.tadpole.northmuse.domain.WebService;
import com.tadpole.northmuse.repository.WebServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing WebService.
 */
@Service
@Transactional
public class WebServiceServiceImpl implements WebServiceService{

    private final Logger log = LoggerFactory.getLogger(WebServiceServiceImpl.class);
    
    private final WebServiceRepository webServiceRepository;

    public WebServiceServiceImpl(WebServiceRepository webServiceRepository) {
        this.webServiceRepository = webServiceRepository;
    }

    /**
     * Save a webService.
     *
     * @param webService the entity to save
     * @return the persisted entity
     */
    @Override
    public WebService save(WebService webService) {
        log.debug("Request to save WebService : {}", webService);
        WebService result = webServiceRepository.save(webService);
        return result;
    }

    /**
     *  Get all the webServices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebService> findAll(Pageable pageable) {
        log.debug("Request to get all WebServices");
        Page<WebService> result = webServiceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one webService by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WebService findOne(Long id) {
        log.debug("Request to get WebService : {}", id);
        WebService webService = webServiceRepository.findOne(id);
        return webService;
    }

    /**
     *  Delete the  webService by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebService : {}", id);
        webServiceRepository.delete(id);
    }
}
