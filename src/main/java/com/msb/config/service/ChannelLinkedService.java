package com.msb.config.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msb.config.domain.ChannelLinked;
import com.msb.config.repository.ChannelLinkedRepository;

@Service
@Transactional
public class ChannelLinkedService {

    private final Logger log = LoggerFactory.getLogger(ChannelLinkedService.class);

    private final ChannelLinkedRepository channelLinkedRepository;
    
    public ChannelLinkedService(ChannelLinkedRepository channelLinkedRepository) {
        this.channelLinkedRepository = channelLinkedRepository;
    }
    
    public void create(ChannelLinked channelLinked) {
        if(!exist(channelLinked)) {
        	log.info(channelLinked.toString());
        	channelLinkedRepository.save(channelLinked);
        } else {
        	log.warn("channel already exist");
        }
    }

    public void create(Set<ChannelLinked> channelLinkeds) {
        for(ChannelLinked channelLinked : channelLinkeds) {
            this.create(channelLinked);
        }
    }

    public Boolean exist(ChannelLinked channelLinked) {
        return channelLinkedRepository.existsChannelLinkedByIdChannel(channelLinked.getIdChannel());
    }
}
