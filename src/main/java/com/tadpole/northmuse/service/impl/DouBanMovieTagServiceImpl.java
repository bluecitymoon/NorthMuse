package com.tadpole.northmuse.service.impl;

import com.tadpole.northmuse.service.DouBanMovieTagService;
import com.tadpole.northmuse.domain.DouBanMovieTag;
import com.tadpole.northmuse.repository.DouBanMovieTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing DouBanMovieTag.
 */
@Service
@Transactional
public class DouBanMovieTagServiceImpl implements DouBanMovieTagService{

    private final Logger log = LoggerFactory.getLogger(DouBanMovieTagServiceImpl.class);

    private final DouBanMovieTagRepository douBanMovieTagRepository;

    public DouBanMovieTagServiceImpl(DouBanMovieTagRepository douBanMovieTagRepository) {
        this.douBanMovieTagRepository = douBanMovieTagRepository;
    }

    /**
     * Save a douBanMovieTag.
     *
     * @param douBanMovieTag the entity to save
     * @return the persisted entity
     */
    @Override
    public DouBanMovieTag save(DouBanMovieTag douBanMovieTag) {
        log.debug("Request to save DouBanMovieTag : {}", douBanMovieTag);
        DouBanMovieTag result = douBanMovieTagRepository.save(douBanMovieTag);

        return result;
    }

    @Override
    public List<DouBanMovieTag> batchSave(List<DouBanMovieTag> douBanMovieTags) {
        log.debug("Request to save DouBanMovieTag : {}", douBanMovieTags);
        List<DouBanMovieTag> result = douBanMovieTagRepository.save(douBanMovieTags);

        return result;
    }


    /**
     *  Get all the douBanMovieTags.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DouBanMovieTag> findAll(Pageable pageable) {
        log.debug("Request to get all DouBanMovieTags");
        Page<DouBanMovieTag> result = douBanMovieTagRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one douBanMovieTag by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DouBanMovieTag findOne(Long id) {
        log.debug("Request to get DouBanMovieTag : {}", id);
        DouBanMovieTag douBanMovieTag = douBanMovieTagRepository.findOne(id);
        return douBanMovieTag;
    }

    /**
     *  Delete the  douBanMovieTag by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DouBanMovieTag : {}", id);
        douBanMovieTagRepository.delete(id);
    }

    @Override
    public void deleteAll() {

        douBanMovieTagRepository.deleteAll();
    }
}
