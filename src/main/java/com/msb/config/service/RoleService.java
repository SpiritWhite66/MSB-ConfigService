package com.msb.config.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msb.config.domain.Role;
import com.msb.config.domain.Role;
import com.msb.config.repository.AliasRepository;
import com.msb.config.repository.RoleRepository;
import com.msb.config.repository.RoleRepository;

@Service
@Transactional
public class RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository RoleRepository;
    
    public RoleService(RoleRepository RoleRepository) {
        this.RoleRepository = RoleRepository;
    }
    
    public void create(Role Role) {
        if(!exist(Role)) {
        	log.info(Role.toString());
        	RoleRepository.save(Role);
        } else {
        	log.warn("channel already exist");
        }
    }

    public void create(Set<Role> Roles) {
        for(Role Role : Roles) {
            this.create(Role);
        }
    }

    public Boolean exist(Role role ) {
        return RoleRepository.existsRoleByIdDiscord(role.getIdDiscord());
    }

}
