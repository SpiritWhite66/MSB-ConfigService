package com.msb.config.web.rest;

import com.msb.config.ConfigServiceApp;
import com.msb.config.domain.ChannelLinked;
import com.msb.config.repository.ChannelLinkedRepository;

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
 * Integration tests for the {@link ChannelLinkedResource} REST controller.
 */
@SpringBootTest(classes = ConfigServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChannelLinkedResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ID_CHANNEL_DISCORD = "AAAAAAAAAA";
    private static final String UPDATED_ID_CHANNEL_DISCORD = "BBBBBBBBBB";

    @Autowired
    private ChannelLinkedRepository channelLinkedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChannelLinkedMockMvc;

    private ChannelLinked channelLinked;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChannelLinked createEntity(EntityManager em) {
        ChannelLinked channelLinked = new ChannelLinked()
            .name(DEFAULT_NAME)
            .idChannelDiscord(DEFAULT_ID_CHANNEL_DISCORD);
        return channelLinked;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChannelLinked createUpdatedEntity(EntityManager em) {
        ChannelLinked channelLinked = new ChannelLinked()
            .name(UPDATED_NAME)
            .idChannelDiscord(UPDATED_ID_CHANNEL_DISCORD);
        return channelLinked;
    }

    @BeforeEach
    public void initTest() {
        channelLinked = createEntity(em);
    }

    @Test
    @Transactional
    public void createChannelLinked() throws Exception {
        int databaseSizeBeforeCreate = channelLinkedRepository.findAll().size();
        // Create the ChannelLinked
        restChannelLinkedMockMvc.perform(post("/api/channel-linkeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(channelLinked)))
            .andExpect(status().isCreated());

        // Validate the ChannelLinked in the database
        List<ChannelLinked> channelLinkedList = channelLinkedRepository.findAll();
        assertThat(channelLinkedList).hasSize(databaseSizeBeforeCreate + 1);
        ChannelLinked testChannelLinked = channelLinkedList.get(channelLinkedList.size() - 1);
        assertThat(testChannelLinked.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChannelLinked.getIdChannelDiscord()).isEqualTo(DEFAULT_ID_CHANNEL_DISCORD);
    }

    @Test
    @Transactional
    public void createChannelLinkedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = channelLinkedRepository.findAll().size();

        // Create the ChannelLinked with an existing ID
        channelLinked.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChannelLinkedMockMvc.perform(post("/api/channel-linkeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(channelLinked)))
            .andExpect(status().isBadRequest());

        // Validate the ChannelLinked in the database
        List<ChannelLinked> channelLinkedList = channelLinkedRepository.findAll();
        assertThat(channelLinkedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChannelLinkeds() throws Exception {
        // Initialize the database
        channelLinkedRepository.saveAndFlush(channelLinked);

        // Get all the channelLinkedList
        restChannelLinkedMockMvc.perform(get("/api/channel-linkeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channelLinked.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].idChannelDiscord").value(hasItem(DEFAULT_ID_CHANNEL_DISCORD)));
    }
    
    @Test
    @Transactional
    public void getChannelLinked() throws Exception {
        // Initialize the database
        channelLinkedRepository.saveAndFlush(channelLinked);

        // Get the channelLinked
        restChannelLinkedMockMvc.perform(get("/api/channel-linkeds/{id}", channelLinked.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(channelLinked.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.idChannelDiscord").value(DEFAULT_ID_CHANNEL_DISCORD));
    }
    @Test
    @Transactional
    public void getNonExistingChannelLinked() throws Exception {
        // Get the channelLinked
        restChannelLinkedMockMvc.perform(get("/api/channel-linkeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChannelLinked() throws Exception {
        // Initialize the database
        channelLinkedRepository.saveAndFlush(channelLinked);

        int databaseSizeBeforeUpdate = channelLinkedRepository.findAll().size();

        // Update the channelLinked
        ChannelLinked updatedChannelLinked = channelLinkedRepository.findById(channelLinked.getId()).get();
        // Disconnect from session so that the updates on updatedChannelLinked are not directly saved in db
        em.detach(updatedChannelLinked);
        updatedChannelLinked
            .name(UPDATED_NAME)
            .idChannelDiscord(UPDATED_ID_CHANNEL_DISCORD);

        restChannelLinkedMockMvc.perform(put("/api/channel-linkeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedChannelLinked)))
            .andExpect(status().isOk());

        // Validate the ChannelLinked in the database
        List<ChannelLinked> channelLinkedList = channelLinkedRepository.findAll();
        assertThat(channelLinkedList).hasSize(databaseSizeBeforeUpdate);
        ChannelLinked testChannelLinked = channelLinkedList.get(channelLinkedList.size() - 1);
        assertThat(testChannelLinked.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChannelLinked.getIdChannelDiscord()).isEqualTo(UPDATED_ID_CHANNEL_DISCORD);
    }

    @Test
    @Transactional
    public void updateNonExistingChannelLinked() throws Exception {
        int databaseSizeBeforeUpdate = channelLinkedRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChannelLinkedMockMvc.perform(put("/api/channel-linkeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(channelLinked)))
            .andExpect(status().isBadRequest());

        // Validate the ChannelLinked in the database
        List<ChannelLinked> channelLinkedList = channelLinkedRepository.findAll();
        assertThat(channelLinkedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChannelLinked() throws Exception {
        // Initialize the database
        channelLinkedRepository.saveAndFlush(channelLinked);

        int databaseSizeBeforeDelete = channelLinkedRepository.findAll().size();

        // Delete the channelLinked
        restChannelLinkedMockMvc.perform(delete("/api/channel-linkeds/{id}", channelLinked.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChannelLinked> channelLinkedList = channelLinkedRepository.findAll();
        assertThat(channelLinkedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
