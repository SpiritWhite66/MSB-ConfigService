package com.msb.config.repository;

import com.msb.config.domain.UserAuthorized;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserAuthorized entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAuthorizedRepository extends JpaRepository<UserAuthorized, Long> {

	Boolean existsUserAuthorizedByIdDiscord(String idDiscord);
}
