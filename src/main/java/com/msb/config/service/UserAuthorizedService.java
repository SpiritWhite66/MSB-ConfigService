package com.msb.config.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msb.config.domain.UserAuthorized;
import com.msb.config.domain.UserAuthorized;
import com.msb.config.repository.AliasRepository;
import com.msb.config.repository.UserAuthorizedRepository;
import com.msb.config.repository.UserAuthorizedRepository;

@Service
@Transactional
public class UserAuthorizedService {

	private final Logger log = LoggerFactory.getLogger(UserAuthorizedService.class);

	private final UserAuthorizedRepository UserAuthorizedRepository;

	public UserAuthorizedService(UserAuthorizedRepository UserAuthorizedRepository) {
        this.UserAuthorizedRepository = UserAuthorizedRepository;
    }

	public void create(UserAuthorized UserAuthorized) {
		if (!exist(UserAuthorized)) {
			log.info(UserAuthorized.toString());
			UserAuthorizedRepository.save(UserAuthorized);
		} else {
			log.warn("channel already exist");
		}
	}

	public void create(Set<UserAuthorized> UserAuthorizeds) {
		for (UserAuthorized UserAuthorized : UserAuthorizeds) {
			this.create(UserAuthorized);
		}
	}

	public Boolean exist(UserAuthorized userAuthorized) {
		return UserAuthorizedRepository.existsUserAuthorizedByIdDiscord(userAuthorized.getIdDiscord());
	}

}
