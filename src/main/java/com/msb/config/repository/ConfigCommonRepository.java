package com.msb.config.repository;

import com.msb.config.domain.ConfigCommon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ConfigCommon entity.
 */
@Repository
public interface ConfigCommonRepository extends JpaRepository<ConfigCommon, Long> {

    @Query(value = "select distinct configCommon from ConfigCommon configCommon left join fetch configCommon.aliases left join fetch configCommon.channelLinkeds left join fetch configCommon.userAuthorizeds left join fetch configCommon.roles",
        countQuery = "select count(distinct configCommon) from ConfigCommon configCommon")
    Page<ConfigCommon> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct configCommon from ConfigCommon configCommon left join fetch configCommon.aliases left join fetch configCommon.channelLinkeds left join fetch configCommon.userAuthorizeds left join fetch configCommon.roles")
    List<ConfigCommon> findAllWithEagerRelationships();

    @Query("select configCommon from ConfigCommon configCommon left join fetch configCommon.aliases left join fetch configCommon.channelLinkeds left join fetch configCommon.userAuthorizeds left join fetch configCommon.roles where configCommon.id =:id")
    Optional<ConfigCommon> findOneWithEagerRelationships(@Param("id") Long id);
}
