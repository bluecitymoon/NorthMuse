package com.tadpole.northmuse.repository;

import com.tadpole.northmuse.domain.DouBanMovieTag;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DouBanMovieTag entity.
 */
@SuppressWarnings("unused")
public interface DouBanMovieTagRepository extends JpaRepository<DouBanMovieTag,Long> {

}
