package com.msb.config.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msb.config.domain.ConfigCommon;
import com.msb.config.repository.ConfigCommonRepository;

@Service
@Transactional
public class ConfigService {

    private final AliasService aliasService;
    private final ChannelLinkedService channelLinkedService;
    private final ConfigCommonRepository configCommonRepository;
    private final RoleService roleService;
    private final UserAuthorizedService userAuthorizedService;


    public ConfigService(AliasService aliasService,
    ChannelLinkedService channelLinkedService,
    ConfigCommonRepository configCommonRepository,
    RoleService roleService,
    UserAuthorizedService userAuthorizedService) {
        this.aliasService = aliasService;
        this.channelLinkedService = channelLinkedService;
        this.configCommonRepository = configCommonRepository;
        this.roleService = roleService;
        this.userAuthorizedService = userAuthorizedService;
    }

    public ConfigCommon create(ConfigCommon pConfig) {
        aliasService.create(pConfig.getAliases());
        channelLinkedService.create(pConfig.getChannelLinkeds());
        roleService.create(pConfig.getRoles());
        userAuthorizedService.create(pConfig.getUserAuthorizeds());
        return configCommonRepository.save(pConfig);
    }
}
