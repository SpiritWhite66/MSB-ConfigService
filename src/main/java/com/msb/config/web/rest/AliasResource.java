package com.msb.config.web.rest;

import com.msb.config.domain.Alias;
import com.msb.config.repository.AliasRepository;
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
 * REST controller for managing {@link com.msb.config.domain.Alias}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AliasResource {

    private final Logger log = LoggerFactory.getLogger(AliasResource.class);

    private static final String ENTITY_NAME = "configServiceAlias";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AliasRepository aliasRepository;

    public AliasResource(AliasRepository aliasRepository) {
        this.aliasRepository = aliasRepository;
    }

    /**
     * {@code POST  /aliases} : Create a new alias.
     *
     * @param alias the alias to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alias, or with status {@code 400 (Bad Request)} if the alias has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aliases")
    public ResponseEntity<Alias> createAlias(@RequestBody Alias alias) throws URISyntaxException {
        log.debug("REST request to save Alias : {}", alias);
        if (alias.getId() != null) {
            throw new BadRequestAlertException("A new alias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Alias result = aliasRepository.save(alias);
        return ResponseEntity.created(new URI("/api/aliases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aliases} : Updates an existing alias.
     *
     * @param alias the alias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alias,
     * or with status {@code 400 (Bad Request)} if the alias is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aliases")
    public ResponseEntity<Alias> updateAlias(@RequestBody Alias alias) throws URISyntaxException {
        log.debug("REST request to update Alias : {}", alias);
        if (alias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Alias result = aliasRepository.save(alias);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alias.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aliases} : get all the aliases.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aliases in body.
     */
    @GetMapping("/aliases")
    public List<Alias> getAllAliases() {
        log.debug("REST request to get all Aliases");
        return aliasRepository.findAll();
    }

    /**
     * {@code GET  /aliases/:id} : get the "id" alias.
     *
     * @param id the id of the alias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aliases/{id}")
    public ResponseEntity<Alias> getAlias(@PathVariable Long id) {
        log.debug("REST request to get Alias : {}", id);
        Optional<Alias> alias = aliasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(alias);
    }

    /**
     * {@code DELETE  /aliases/:id} : delete the "id" alias.
     *
     * @param id the id of the alias to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aliases/{id}")
    public ResponseEntity<Void> deleteAlias(@PathVariable Long id) {
        log.debug("REST request to delete Alias : {}", id);
        aliasRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
