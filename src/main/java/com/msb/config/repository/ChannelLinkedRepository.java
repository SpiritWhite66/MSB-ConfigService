package com.msb.config.repository;

import com.msb.config.domain.ChannelLinked;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ChannelLinked entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChannelLinkedRepository extends JpaRepository<ChannelLinked, Long> {
}
