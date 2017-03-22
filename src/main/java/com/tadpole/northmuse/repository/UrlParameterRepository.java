package com.tadpole.northmuse.repository;

import com.tadpole.northmuse.domain.UrlParameter;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UrlParameter entity.
 */
@SuppressWarnings("unused")
public interface UrlParameterRepository extends JpaRepository<UrlParameter,Long> {

}
