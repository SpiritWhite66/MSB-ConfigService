package com.msb.config.web.rest;

import com.msb.config.domain.UserAuthorized;
import com.msb.config.repository.UserAuthorizedRepository;
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
 * REST controller for managing {@link com.msb.config.domain.UserAuthorized}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserAuthorizedResource {

    private final Logger log = LoggerFactory.getLogger(UserAuthorizedResource.class);

    private static final String ENTITY_NAME = "configServiceUserAuthorized";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAuthorizedRepository userAuthorizedRepository;

    public UserAuthorizedResource(UserAuthorizedRepository userAuthorizedRepository) {
        this.userAuthorizedRepository = userAuthorizedRepository;
    }

    /**
     * {@code POST  /user-authorizeds} : Create a new userAuthorized.
     *
     * @param userAuthorized the userAuthorized to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAuthorized, or with status {@code 400 (Bad Request)} if the userAuthorized has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-authorizeds")
    public ResponseEntity<UserAuthorized> createUserAuthorized(@Valid @RequestBody UserAuthorized userAuthorized) throws URISyntaxException {
        log.debug("REST request to save UserAuthorized : {}", userAuthorized);
        if (userAuthorized.getId() != null) {
            throw new BadRequestAlertException("A new userAuthorized cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAuthorized result = userAuthorizedRepository.save(userAuthorized);
        return ResponseEntity.created(new URI("/api/user-authorizeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-authorizeds} : Updates an existing userAuthorized.
     *
     * @param userAuthorized the userAuthorized to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAuthorized,
     * or with status {@code 400 (Bad Request)} if the userAuthorized is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAuthorized couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-authorizeds")
    public ResponseEntity<UserAuthorized> updateUserAuthorized(@Valid @RequestBody UserAuthorized userAuthorized) throws URISyntaxException {
        log.debug("REST request to update UserAuthorized : {}", userAuthorized);
        if (userAuthorized.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAuthorized result = userAuthorizedRepository.save(userAuthorized);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAuthorized.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-authorizeds} : get all the userAuthorizeds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAuthorizeds in body.
     */
    @GetMapping("/user-authorizeds")
    public List<UserAuthorized> getAllUserAuthorizeds() {
        log.debug("REST request to get all UserAuthorizeds");
        return userAuthorizedRepository.findAll();
    }

    /**
     * {@code GET  /user-authorizeds/:id} : get the "id" userAuthorized.
     *
     * @param id the id of the userAuthorized to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAuthorized, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-authorizeds/{id}")
    public ResponseEntity<UserAuthorized> getUserAuthorized(@PathVariable Long id) {
        log.debug("REST request to get UserAuthorized : {}", id);
        Optional<UserAuthorized> userAuthorized = userAuthorizedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userAuthorized);
    }

    /**
     * {@code DELETE  /user-authorizeds/:id} : delete the "id" userAuthorized.
     *
     * @param id the id of the userAuthorized to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-authorizeds/{id}")
    public ResponseEntity<Void> deleteUserAuthorized(@PathVariable Long id) {
        log.debug("REST request to delete UserAuthorized : {}", id);
        userAuthorizedRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
