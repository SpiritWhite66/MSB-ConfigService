package com.msb.config.web.rest;

import com.msb.config.ConfigServiceApp;
import com.msb.config.domain.UserAuthorized;
import com.msb.config.repository.UserAuthorizedRepository;

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
 * Integration tests for the {@link UserAuthorizedResource} REST controller.
 */
@SpringBootTest(classes = ConfigServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserAuthorizedResourceIT {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_ID_USER_DISCORD = "AAAAAAAAAA";
    private static final String UPDATED_ID_USER_DISCORD = "BBBBBBBBBB";

    @Autowired
    private UserAuthorizedRepository userAuthorizedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAuthorizedMockMvc;

    private UserAuthorized userAuthorized;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAuthorized createEntity(EntityManager em) {
        UserAuthorized userAuthorized = new UserAuthorized()
            .user(DEFAULT_USER)
            .idUserDiscord(DEFAULT_ID_USER_DISCORD);
        return userAuthorized;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAuthorized createUpdatedEntity(EntityManager em) {
        UserAuthorized userAuthorized = new UserAuthorized()
            .user(UPDATED_USER)
            .idUserDiscord(UPDATED_ID_USER_DISCORD);
        return userAuthorized;
    }

    @BeforeEach
    public void initTest() {
        userAuthorized = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAuthorized() throws Exception {
        int databaseSizeBeforeCreate = userAuthorizedRepository.findAll().size();
        // Create the UserAuthorized
        restUserAuthorizedMockMvc.perform(post("/api/user-authorizeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthorized)))
            .andExpect(status().isCreated());

        // Validate the UserAuthorized in the database
        List<UserAuthorized> userAuthorizedList = userAuthorizedRepository.findAll();
        assertThat(userAuthorizedList).hasSize(databaseSizeBeforeCreate + 1);
        UserAuthorized testUserAuthorized = userAuthorizedList.get(userAuthorizedList.size() - 1);
        assertThat(testUserAuthorized.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testUserAuthorized.getIdUserDiscord()).isEqualTo(DEFAULT_ID_USER_DISCORD);
    }

    @Test
    @Transactional
    public void createUserAuthorizedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAuthorizedRepository.findAll().size();

        // Create the UserAuthorized with an existing ID
        userAuthorized.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAuthorizedMockMvc.perform(post("/api/user-authorizeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthorized)))
            .andExpect(status().isBadRequest());

        // Validate the UserAuthorized in the database
        List<UserAuthorized> userAuthorizedList = userAuthorizedRepository.findAll();
        assertThat(userAuthorizedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAuthorizedRepository.findAll().size();
        // set the field null
        userAuthorized.setUser(null);

        // Create the UserAuthorized, which fails.


        restUserAuthorizedMockMvc.perform(post("/api/user-authorizeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthorized)))
            .andExpect(status().isBadRequest());

        List<UserAuthorized> userAuthorizedList = userAuthorizedRepository.findAll();
        assertThat(userAuthorizedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdUserDiscordIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAuthorizedRepository.findAll().size();
        // set the field null
        userAuthorized.setIdUserDiscord(null);

        // Create the UserAuthorized, which fails.


        restUserAuthorizedMockMvc.perform(post("/api/user-authorizeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthorized)))
            .andExpect(status().isBadRequest());

        List<UserAuthorized> userAuthorizedList = userAuthorizedRepository.findAll();
        assertThat(userAuthorizedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserAuthorizeds() throws Exception {
        // Initialize the database
        userAuthorizedRepository.saveAndFlush(userAuthorized);

        // Get all the userAuthorizedList
        restUserAuthorizedMockMvc.perform(get("/api/user-authorizeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAuthorized.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].idUserDiscord").value(hasItem(DEFAULT_ID_USER_DISCORD)));
    }
    
    @Test
    @Transactional
    public void getUserAuthorized() throws Exception {
        // Initialize the database
        userAuthorizedRepository.saveAndFlush(userAuthorized);

        // Get the userAuthorized
        restUserAuthorizedMockMvc.perform(get("/api/user-authorizeds/{id}", userAuthorized.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAuthorized.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.idUserDiscord").value(DEFAULT_ID_USER_DISCORD));
    }
    @Test
    @Transactional
    public void getNonExistingUserAuthorized() throws Exception {
        // Get the userAuthorized
        restUserAuthorizedMockMvc.perform(get("/api/user-authorizeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAuthorized() throws Exception {
        // Initialize the database
        userAuthorizedRepository.saveAndFlush(userAuthorized);

        int databaseSizeBeforeUpdate = userAuthorizedRepository.findAll().size();

        // Update the userAuthorized
        UserAuthorized updatedUserAuthorized = userAuthorizedRepository.findById(userAuthorized.getId()).get();
        // Disconnect from session so that the updates on updatedUserAuthorized are not directly saved in db
        em.detach(updatedUserAuthorized);
        updatedUserAuthorized
            .user(UPDATED_USER)
            .idUserDiscord(UPDATED_ID_USER_DISCORD);

        restUserAuthorizedMockMvc.perform(put("/api/user-authorizeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserAuthorized)))
            .andExpect(status().isOk());

        // Validate the UserAuthorized in the database
        List<UserAuthorized> userAuthorizedList = userAuthorizedRepository.findAll();
        assertThat(userAuthorizedList).hasSize(databaseSizeBeforeUpdate);
        UserAuthorized testUserAuthorized = userAuthorizedList.get(userAuthorizedList.size() - 1);
        assertThat(testUserAuthorized.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testUserAuthorized.getIdUserDiscord()).isEqualTo(UPDATED_ID_USER_DISCORD);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAuthorized() throws Exception {
        int databaseSizeBeforeUpdate = userAuthorizedRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAuthorizedMockMvc.perform(put("/api/user-authorizeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthorized)))
            .andExpect(status().isBadRequest());

        // Validate the UserAuthorized in the database
        List<UserAuthorized> userAuthorizedList = userAuthorizedRepository.findAll();
        assertThat(userAuthorizedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserAuthorized() throws Exception {
        // Initialize the database
        userAuthorizedRepository.saveAndFlush(userAuthorized);

        int databaseSizeBeforeDelete = userAuthorizedRepository.findAll().size();

        // Delete the userAuthorized
        restUserAuthorizedMockMvc.perform(delete("/api/user-authorizeds/{id}", userAuthorized.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAuthorized> userAuthorizedList = userAuthorizedRepository.findAll();
        assertThat(userAuthorizedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
