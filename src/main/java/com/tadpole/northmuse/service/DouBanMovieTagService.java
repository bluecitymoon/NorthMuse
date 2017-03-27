package com.tadpole.northmuse.service;

import com.tadpole.northmuse.domain.DouBanMovieTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing DouBanMovieTag.
 */
public interface DouBanMovieTagService {

    /**
     * Save a douBanMovieTag.
     *
     * @param douBanMovieTag the entity to save
     * @return the persisted entity
     */
    DouBanMovieTag save(DouBanMovieTag douBanMovieTag);

    List<DouBanMovieTag> batchSave(List<DouBanMovieTag> douBanMovieTags);

    /**
     *  Get all the douBanMovieTags.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DouBanMovieTag> findAll(Pageable pageable);

    /**
     *  Get the "id" douBanMovieTag.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DouBanMovieTag findOne(Long id);

    /**
     *  Delete the "id" douBanMovieTag.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void deleteAll();
}
