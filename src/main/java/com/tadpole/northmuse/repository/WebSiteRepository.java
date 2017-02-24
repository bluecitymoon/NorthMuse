package com.tadpole.northmuse.repository;

import com.tadpole.northmuse.domain.WebSite;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WebSite entity.
 */
@SuppressWarnings("unused")
public interface WebSiteRepository extends JpaRepository<WebSite,Long> {

}
