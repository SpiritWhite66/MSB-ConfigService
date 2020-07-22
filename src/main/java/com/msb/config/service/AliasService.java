package com.msb.config.service;

import com.msb.config.domain.Alias;
import com.msb.config.repository.AliasRepository;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AliasService {

    private final Logger log = LoggerFactory.getLogger(AliasService.class);

    private final AliasRepository aliasRepository;

    public AliasService(AliasRepository aliasRepository) {
        this.aliasRepository = aliasRepository;
    }

    public void create(Alias alias) {
        if(!exist(alias)) {
            aliasRepository.save(alias);
        } else {
        	log.warn("alias already exist");
        }
    }

    public void create(Set<Alias> pListeAlias) {
        for(Alias alias : pListeAlias) {
            this.create(alias);
        }
    }

    public Boolean exist(Alias pAlias) {
        return aliasRepository.existsAliasByAlias(pAlias.getAlias());
    }
}
