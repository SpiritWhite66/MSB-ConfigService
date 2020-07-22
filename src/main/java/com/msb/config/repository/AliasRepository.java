package com.msb.config.repository;

import com.msb.config.domain.Alias;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data  repository for the Alias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AliasRepository extends JpaRepository<Alias, Long> {

    /**
     * Trouve un Alias par son nom
     * @param pAlias
     * @return
     */
    Optional<Alias> findOneByAlias(@Param("alias") Long pAlias);

    /**
     * Vérifie si l'alias existe par son nom
     * @param alias
     * @return
     */
    boolean existsAliasByAlias(String alias);
}
