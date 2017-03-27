package com.tadpole.northmuse.web.rest;

import com.tadpole.northmuse.NorthMuseApp;

import com.tadpole.northmuse.domain.DouBanMovieTag;
import com.tadpole.northmuse.repository.DouBanMovieTagRepository;
import com.tadpole.northmuse.service.DouBanMovieTagService;
import com.tadpole.northmuse.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DouBanMovieTagResource REST controller.
 *
 * @see DouBanMovieTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NorthMuseApp.class)
public class DouBanMovieTagResourceIntTest {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    @Autowired
    private DouBanMovieTagRepository douBanMovieTagRepository;

    @Autowired
    private DouBanMovieTagService douBanMovieTagService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDouBanMovieTagMockMvc;

    private DouBanMovieTag douBanMovieTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DouBanMovieTagResource douBanMovieTagResource = new DouBanMovieTagResource(douBanMovieTagService);
        this.restDouBanMovieTagMockMvc = MockMvcBuilders.standaloneSetup(douBanMovieTagResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DouBanMovieTag createEntity(EntityManager em) {
        DouBanMovieTag douBanMovieTag = new DouBanMovieTag()
                .tag(DEFAULT_TAG);
        return douBanMovieTag;
    }

    @Before
    public void initTest() {
        douBanMovieTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createDouBanMovieTag() throws Exception {
        int databaseSizeBeforeCreate = douBanMovieTagRepository.findAll().size();

        // Create the DouBanMovieTag

        restDouBanMovieTagMockMvc.perform(post("/api/dou-ban-movie-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(douBanMovieTag)))
            .andExpect(status().isCreated());

        // Validate the DouBanMovieTag in the database
        List<DouBanMovieTag> douBanMovieTagList = douBanMovieTagRepository.findAll();
        assertThat(douBanMovieTagList).hasSize(databaseSizeBeforeCreate + 1);
        DouBanMovieTag testDouBanMovieTag = douBanMovieTagList.get(douBanMovieTagList.size() - 1);
        assertThat(testDouBanMovieTag.getTag()).isEqualTo(DEFAULT_TAG);
    }

    @Test
    @Transactional
    public void createDouBanMovieTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = douBanMovieTagRepository.findAll().size();

        // Create the DouBanMovieTag with an existing ID
        DouBanMovieTag existingDouBanMovieTag = new DouBanMovieTag();
        existingDouBanMovieTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDouBanMovieTagMockMvc.perform(post("/api/dou-ban-movie-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDouBanMovieTag)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DouBanMovieTag> douBanMovieTagList = douBanMovieTagRepository.findAll();
        assertThat(douBanMovieTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDouBanMovieTags() throws Exception {
        // Initialize the database
        douBanMovieTagRepository.saveAndFlush(douBanMovieTag);

        // Get all the douBanMovieTagList
        restDouBanMovieTagMockMvc.perform(get("/api/dou-ban-movie-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(douBanMovieTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())));
    }

    @Test
    @Transactional
    public void getDouBanMovieTag() throws Exception {
        // Initialize the database
        douBanMovieTagRepository.saveAndFlush(douBanMovieTag);

        // Get the douBanMovieTag
        restDouBanMovieTagMockMvc.perform(get("/api/dou-ban-movie-tags/{id}", douBanMovieTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(douBanMovieTag.getId().intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDouBanMovieTag() throws Exception {
        // Get the douBanMovieTag
        restDouBanMovieTagMockMvc.perform(get("/api/dou-ban-movie-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDouBanMovieTag() throws Exception {
        // Initialize the database
        douBanMovieTagService.save(douBanMovieTag);

        int databaseSizeBeforeUpdate = douBanMovieTagRepository.findAll().size();

        // Update the douBanMovieTag
        DouBanMovieTag updatedDouBanMovieTag = douBanMovieTagRepository.findOne(douBanMovieTag.getId());
        updatedDouBanMovieTag
                .tag(UPDATED_TAG);

        restDouBanMovieTagMockMvc.perform(put("/api/dou-ban-movie-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDouBanMovieTag)))
            .andExpect(status().isOk());

        // Validate the DouBanMovieTag in the database
        List<DouBanMovieTag> douBanMovieTagList = douBanMovieTagRepository.findAll();
        assertThat(douBanMovieTagList).hasSize(databaseSizeBeforeUpdate);
        DouBanMovieTag testDouBanMovieTag = douBanMovieTagList.get(douBanMovieTagList.size() - 1);
        assertThat(testDouBanMovieTag.getTag()).isEqualTo(UPDATED_TAG);
    }

    @Test
    @Transactional
    public void updateNonExistingDouBanMovieTag() throws Exception {
        int databaseSizeBeforeUpdate = douBanMovieTagRepository.findAll().size();

        // Create the DouBanMovieTag

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDouBanMovieTagMockMvc.perform(put("/api/dou-ban-movie-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(douBanMovieTag)))
            .andExpect(status().isCreated());

        // Validate the DouBanMovieTag in the database
        List<DouBanMovieTag> douBanMovieTagList = douBanMovieTagRepository.findAll();
        assertThat(douBanMovieTagList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDouBanMovieTag() throws Exception {
        // Initialize the database
        douBanMovieTagService.save(douBanMovieTag);

        int databaseSizeBeforeDelete = douBanMovieTagRepository.findAll().size();

        // Get the douBanMovieTag
        restDouBanMovieTagMockMvc.perform(delete("/api/dou-ban-movie-tags/{id}", douBanMovieTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DouBanMovieTag> douBanMovieTagList = douBanMovieTagRepository.findAll();
        assertThat(douBanMovieTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DouBanMovieTag.class);
    }
}
