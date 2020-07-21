package com.msb.config.repository;

import com.msb.config.domain.Alias;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Alias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AliasRepository extends JpaRepository<Alias, Long> {
}
