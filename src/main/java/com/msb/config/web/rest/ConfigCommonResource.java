package com.msb.config.web.rest;

import com.msb.config.domain.ConfigCommon;
import com.msb.config.repository.ConfigCommonRepository;
import com.msb.config.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.msb.config.domain.ConfigCommon}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConfigCommonResource {

    private final Logger log = LoggerFactory.getLogger(ConfigCommonResource.class);

    private static final String ENTITY_NAME = "configServiceConfigCommon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigCommonRepository configCommonRepository;

    public ConfigCommonResource(ConfigCommonRepository configCommonRepository) {
        this.configCommonRepository = configCommonRepository;
    }

    /**
     * {@code POST  /config-commons} : Create a new configCommon.
     *
     * @param configCommon the configCommon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configCommon, or with status {@code 400 (Bad Request)} if the configCommon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/config-commons")
    public ResponseEntity<ConfigCommon> createConfigCommon(@Valid @RequestBody ConfigCommon configCommon) throws URISyntaxException {
        log.debug("REST request to save ConfigCommon : {}", configCommon);
        if (configCommon.getId() != null) {
            throw new BadRequestAlertException("A new configCommon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigCommon result = configCommonRepository.save(configCommon);
        return ResponseEntity.created(new URI("/api/config-commons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /config-commons} : Updates an existing configCommon.
     *
     * @param configCommon the configCommon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configCommon,
     * or with status {@code 400 (Bad Request)} if the configCommon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configCommon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/config-commons")
    public ResponseEntity<ConfigCommon> updateConfigCommon(@Valid @RequestBody ConfigCommon configCommon) throws URISyntaxException {
        log.debug("REST request to update ConfigCommon : {}", configCommon);
        if (configCommon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConfigCommon result = configCommonRepository.save(configCommon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, configCommon.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /config-commons} : get all the configCommons.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configCommons in body.
     */
    @GetMapping("/config-commons")
    public List<ConfigCommon> getAllConfigCommons(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ConfigCommons");
        return configCommonRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /config-commons/:id} : get the "id" configCommon.
     *
     * @param id the id of the configCommon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configCommon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/config-commons/{id}")
    public ResponseEntity<ConfigCommon> getConfigCommon(@PathVariable Long id) {
        log.debug("REST request to get ConfigCommon : {}", id);
        Optional<ConfigCommon> configCommon = configCommonRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(configCommon);
    }

    /**
     * {@code DELETE  /config-commons/:id} : delete the "id" configCommon.
     *
     * @param id the id of the configCommon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/config-commons/{id}")
    public ResponseEntity<Void> deleteConfigCommon(@PathVariable Long id) {
        log.debug("REST request to delete ConfigCommon : {}", id);
        configCommonRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
