package com.tadpole.northmuse.repository;

import com.tadpole.northmuse.domain.WebSiteUrl;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WebSiteUrl entity.
 */
@SuppressWarnings("unused")
public interface WebSiteUrlRepository extends JpaRepository<WebSiteUrl,Long> {

}
