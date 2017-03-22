package com.tadpole.northmuse.service.impl;

import com.tadpole.northmuse.service.WebSiteUrlService;
import com.tadpole.northmuse.domain.WebSiteUrl;
import com.tadpole.northmuse.repository.WebSiteUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing WebSiteUrl.
 */
@Service
@Transactional
public class WebSiteUrlServiceImpl implements WebSiteUrlService{

    private final Logger log = LoggerFactory.getLogger(WebSiteUrlServiceImpl.class);
    
    private final WebSiteUrlRepository webSiteUrlRepository;

    public WebSiteUrlServiceImpl(WebSiteUrlRepository webSiteUrlRepository) {
        this.webSiteUrlRepository = webSiteUrlRepository;
    }

    /**
     * Save a webSiteUrl.
     *
     * @param webSiteUrl the entity to save
     * @return the persisted entity
     */
    @Override
    public WebSiteUrl save(WebSiteUrl webSiteUrl) {
        log.debug("Request to save WebSiteUrl : {}", webSiteUrl);
        WebSiteUrl result = webSiteUrlRepository.save(webSiteUrl);
        return result;
    }

    /**
     *  Get all the webSiteUrls.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebSiteUrl> findAll(Pageable pageable) {
        log.debug("Request to get all WebSiteUrls");
        Page<WebSiteUrl> result = webSiteUrlRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one webSiteUrl by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WebSiteUrl findOne(Long id) {
        log.debug("Request to get WebSiteUrl : {}", id);
        WebSiteUrl webSiteUrl = webSiteUrlRepository.findOne(id);
        return webSiteUrl;
    }

    /**
     *  Delete the  webSiteUrl by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebSiteUrl : {}", id);
        webSiteUrlRepository.delete(id);
    }
}
