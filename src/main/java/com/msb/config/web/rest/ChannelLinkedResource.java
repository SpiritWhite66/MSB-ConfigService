package com.msb.config.web.rest;

import com.msb.config.domain.ChannelLinked;
import com.msb.config.repository.ChannelLinkedRepository;
import com.msb.config.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.msb.config.domain.ChannelLinked}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChannelLinkedResource {

    private final Logger log = LoggerFactory.getLogger(ChannelLinkedResource.class);

    private static final String ENTITY_NAME = "configServiceChannelLinked";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChannelLinkedRepository channelLinkedRepository;

    public ChannelLinkedResource(ChannelLinkedRepository channelLinkedRepository) {
        this.channelLinkedRepository = channelLinkedRepository;
    }

    /**
     * {@code POST  /channel-linkeds} : Create a new channelLinked.
     *
     * @param channelLinked the channelLinked to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new channelLinked, or with status {@code 400 (Bad Request)} if the channelLinked has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/channel-linkeds")
    public ResponseEntity<ChannelLinked> createChannelLinked(@RequestBody ChannelLinked channelLinked) throws URISyntaxException {
        log.debug("REST request to save ChannelLinked : {}", channelLinked);
        if (channelLinked.getId() != null) {
            throw new BadRequestAlertException("A new channelLinked cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChannelLinked result = channelLinkedRepository.save(channelLinked);
        return ResponseEntity.created(new URI("/api/channel-linkeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /channel-linkeds} : Updates an existing channelLinked.
     *
     * @param channelLinked the channelLinked to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated channelLinked,
     * or with status {@code 400 (Bad Request)} if the channelLinked is not valid,
     * or with status {@code 500 (Internal Server Error)} if the channelLinked couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/channel-linkeds")
    public ResponseEntity<ChannelLinked> updateChannelLinked(@RequestBody ChannelLinked channelLinked) throws URISyntaxException {
        log.debug("REST request to update ChannelLinked : {}", channelLinked);
        if (channelLinked.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChannelLinked result = channelLinkedRepository.save(channelLinked);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, channelLinked.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /channel-linkeds} : get all the channelLinkeds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of channelLinkeds in body.
     */
    @GetMapping("/channel-linkeds")
    public List<ChannelLinked> getAllChannelLinkeds() {
        log.debug("REST request to get all ChannelLinkeds");
        return channelLinkedRepository.findAll();
    }

    /**
     * {@code GET  /channel-linkeds/:id} : get the "id" channelLinked.
     *
     * @param id the id of the channelLinked to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the channelLinked, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/channel-linkeds/{id}")
    public ResponseEntity<ChannelLinked> getChannelLinked(@PathVariable Long id) {
        log.debug("REST request to get ChannelLinked : {}", id);
        Optional<ChannelLinked> channelLinked = channelLinkedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(channelLinked);
    }

    /**
     * {@code DELETE  /channel-linkeds/:id} : delete the "id" channelLinked.
     *
     * @param id the id of the channelLinked to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/channel-linkeds/{id}")
    public ResponseEntity<Void> deleteChannelLinked(@PathVariable Long id) {
        log.debug("REST request to delete ChannelLinked : {}", id);
        channelLinkedRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
