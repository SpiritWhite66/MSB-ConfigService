package com.msb.config.web.rest;

import com.msb.config.ConfigServiceApp;
import com.msb.config.domain.Alias;
import com.msb.config.repository.AliasRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AliasResource} REST controller.
 */
@SpringBootTest(classes = ConfigServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AliasResourceIT {

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    @Autowired
    private AliasRepository aliasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAliasMockMvc;

    private Alias alias;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alias createEntity(EntityManager em) {
        Alias alias = new Alias()
            .alias(DEFAULT_ALIAS);
        return alias;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alias createUpdatedEntity(EntityManager em) {
        Alias alias = new Alias()
            .alias(UPDATED_ALIAS);
        return alias;
    }

    @BeforeEach
    public void initTest() {
        alias = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlias() throws Exception {
        int databaseSizeBeforeCreate = aliasRepository.findAll().size();
        // Create the Alias
        restAliasMockMvc.perform(post("/api/aliases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alias)))
            .andExpect(status().isCreated());

        // Validate the Alias in the database
        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeCreate + 1);
        Alias testAlias = aliasList.get(aliasList.size() - 1);
        assertThat(testAlias.getAlias()).isEqualTo(DEFAULT_ALIAS);
    }

    @Test
    @Transactional
    public void createAliasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aliasRepository.findAll().size();

        // Create the Alias with an existing ID
        alias.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAliasMockMvc.perform(post("/api/aliases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alias)))
            .andExpect(status().isBadRequest());

        // Validate the Alias in the database
        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAliases() throws Exception {
        // Initialize the database
        aliasRepository.saveAndFlush(alias);

        // Get all the aliasList
        restAliasMockMvc.perform(get("/api/aliases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alias.getId().intValue())))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)));
    }
    
    @Test
    @Transactional
    public void getAlias() throws Exception {
        // Initialize the database
        aliasRepository.saveAndFlush(alias);

        // Get the alias
        restAliasMockMvc.perform(get("/api/aliases/{id}", alias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alias.getId().intValue()))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS));
    }
    @Test
    @Transactional
    public void getNonExistingAlias() throws Exception {
        // Get the alias
        restAliasMockMvc.perform(get("/api/aliases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlias() throws Exception {
        // Initialize the database
        aliasRepository.saveAndFlush(alias);

        int databaseSizeBeforeUpdate = aliasRepository.findAll().size();

        // Update the alias
        Alias updatedAlias = aliasRepository.findById(alias.getId()).get();
        // Disconnect from session so that the updates on updatedAlias are not directly saved in db
        em.detach(updatedAlias);
        updatedAlias
            .alias(UPDATED_ALIAS);

        restAliasMockMvc.perform(put("/api/aliases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlias)))
            .andExpect(status().isOk());

        // Validate the Alias in the database
        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeUpdate);
        Alias testAlias = aliasList.get(aliasList.size() - 1);
        assertThat(testAlias.getAlias()).isEqualTo(UPDATED_ALIAS);
    }

    @Test
    @Transactional
    public void updateNonExistingAlias() throws Exception {
        int databaseSizeBeforeUpdate = aliasRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAliasMockMvc.perform(put("/api/aliases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alias)))
            .andExpect(status().isBadRequest());

        // Validate the Alias in the database
        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlias() throws Exception {
        // Initialize the database
        aliasRepository.saveAndFlush(alias);

        int databaseSizeBeforeDelete = aliasRepository.findAll().size();

        // Delete the alias
        restAliasMockMvc.perform(delete("/api/aliases/{id}", alias.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
