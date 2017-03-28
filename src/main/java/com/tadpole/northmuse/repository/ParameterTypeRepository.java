package com.tadpole.northmuse.repository;

import com.tadpole.northmuse.domain.ParameterType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ParameterType entity.
 */
@SuppressWarnings("unused")
public interface ParameterTypeRepository extends JpaRepository<ParameterType,Long> {

}
