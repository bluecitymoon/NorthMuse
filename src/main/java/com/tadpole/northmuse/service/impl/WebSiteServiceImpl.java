package com.tadpole.northmuse.service.impl;

import com.tadpole.northmuse.service.WebSiteService;
import com.tadpole.northmuse.domain.WebSite;
import com.tadpole.northmuse.repository.WebSiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing WebSite.
 */
@Service
@Transactional
public class WebSiteServiceImpl implements WebSiteService{

    private final Logger log = LoggerFactory.getLogger(WebSiteServiceImpl.class);
    
    private final WebSiteRepository webSiteRepository;

    public WebSiteServiceImpl(WebSiteRepository webSiteRepository) {
        this.webSiteRepository = webSiteRepository;
    }

    /**
     * Save a webSite.
     *
     * @param webSite the entity to save
     * @return the persisted entity
     */
    @Override
    public WebSite save(WebSite webSite) {
        log.debug("Request to save WebSite : {}", webSite);
        WebSite result = webSiteRepository.save(webSite);
        return result;
    }

    /**
     *  Get all the webSites.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebSite> findAll(Pageable pageable) {
        log.debug("Request to get all WebSites");
        Page<WebSite> result = webSiteRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one webSite by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WebSite findOne(Long id) {
        log.debug("Request to get WebSite : {}", id);
        WebSite webSite = webSiteRepository.findOne(id);
        return webSite;
    }

    /**
     *  Delete the  webSite by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebSite : {}", id);
        webSiteRepository.delete(id);
    }
}
