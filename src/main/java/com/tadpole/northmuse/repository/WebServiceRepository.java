package com.tadpole.northmuse.repository;

import com.tadpole.northmuse.domain.WebService;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WebService entity.
 */
@SuppressWarnings("unused")
public interface WebServiceRepository extends JpaRepository<WebService,Long> {

}
