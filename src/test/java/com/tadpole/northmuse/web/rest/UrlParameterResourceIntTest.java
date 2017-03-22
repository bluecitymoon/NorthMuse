package com.tadpole.northmuse.web.rest;

import com.tadpole.northmuse.NorthMuseApp;

import com.tadpole.northmuse.domain.UrlParameter;
import com.tadpole.northmuse.repository.UrlParameterRepository;
import com.tadpole.northmuse.service.UrlParameterService;
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
 * Test class for the UrlParameterResource REST controller.
 *
 * @see UrlParameterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NorthMuseApp.class)
public class UrlParameterResourceIntTest {

    private static final String DEFAULT_PARAM_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PARAM_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    @Autowired
    private UrlParameterRepository urlParameterRepository;

    @Autowired
    private UrlParameterService urlParameterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUrlParameterMockMvc;

    private UrlParameter urlParameter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UrlParameterResource urlParameterResource = new UrlParameterResource(urlParameterService);
        this.restUrlParameterMockMvc = MockMvcBuilders.standaloneSetup(urlParameterResource)
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
    public static UrlParameter createEntity(EntityManager em) {
        UrlParameter urlParameter = new UrlParameter()
                .paramKey(DEFAULT_PARAM_KEY)
                .paramValue(DEFAULT_PARAM_VALUE)
                .defaultValue(DEFAULT_DEFAULT_VALUE);
        return urlParameter;
    }

    @Before
    public void initTest() {
        urlParameter = createEntity(em);
    }

    @Test
    @Transactional
    public void createUrlParameter() throws Exception {
        int databaseSizeBeforeCreate = urlParameterRepository.findAll().size();

        // Create the UrlParameter

        restUrlParameterMockMvc.perform(post("/api/url-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urlParameter)))
            .andExpect(status().isCreated());

        // Validate the UrlParameter in the database
        List<UrlParameter> urlParameterList = urlParameterRepository.findAll();
        assertThat(urlParameterList).hasSize(databaseSizeBeforeCreate + 1);
        UrlParameter testUrlParameter = urlParameterList.get(urlParameterList.size() - 1);
        assertThat(testUrlParameter.getParamKey()).isEqualTo(DEFAULT_PARAM_KEY);
        assertThat(testUrlParameter.getParamValue()).isEqualTo(DEFAULT_PARAM_VALUE);
        assertThat(testUrlParameter.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createUrlParameterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = urlParameterRepository.findAll().size();

        // Create the UrlParameter with an existing ID
        UrlParameter existingUrlParameter = new UrlParameter();
        existingUrlParameter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUrlParameterMockMvc.perform(post("/api/url-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUrlParameter)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UrlParameter> urlParameterList = urlParameterRepository.findAll();
        assertThat(urlParameterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUrlParameters() throws Exception {
        // Initialize the database
        urlParameterRepository.saveAndFlush(urlParameter);

        // Get all the urlParameterList
        restUrlParameterMockMvc.perform(get("/api/url-parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(urlParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].paramKey").value(hasItem(DEFAULT_PARAM_KEY.toString())))
            .andExpect(jsonPath("$.[*].paramValue").value(hasItem(DEFAULT_PARAM_VALUE.toString())))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getUrlParameter() throws Exception {
        // Initialize the database
        urlParameterRepository.saveAndFlush(urlParameter);

        // Get the urlParameter
        restUrlParameterMockMvc.perform(get("/api/url-parameters/{id}", urlParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(urlParameter.getId().intValue()))
            .andExpect(jsonPath("$.paramKey").value(DEFAULT_PARAM_KEY.toString()))
            .andExpect(jsonPath("$.paramValue").value(DEFAULT_PARAM_VALUE.toString()))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUrlParameter() throws Exception {
        // Get the urlParameter
        restUrlParameterMockMvc.perform(get("/api/url-parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUrlParameter() throws Exception {
        // Initialize the database
        urlParameterService.save(urlParameter);

        int databaseSizeBeforeUpdate = urlParameterRepository.findAll().size();

        // Update the urlParameter
        UrlParameter updatedUrlParameter = urlParameterRepository.findOne(urlParameter.getId());
        updatedUrlParameter
                .paramKey(UPDATED_PARAM_KEY)
                .paramValue(UPDATED_PARAM_VALUE)
                .defaultValue(UPDATED_DEFAULT_VALUE);

        restUrlParameterMockMvc.perform(put("/api/url-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUrlParameter)))
            .andExpect(status().isOk());

        // Validate the UrlParameter in the database
        List<UrlParameter> urlParameterList = urlParameterRepository.findAll();
        assertThat(urlParameterList).hasSize(databaseSizeBeforeUpdate);
        UrlParameter testUrlParameter = urlParameterList.get(urlParameterList.size() - 1);
        assertThat(testUrlParameter.getParamKey()).isEqualTo(UPDATED_PARAM_KEY);
        assertThat(testUrlParameter.getParamValue()).isEqualTo(UPDATED_PARAM_VALUE);
        assertThat(testUrlParameter.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingUrlParameter() throws Exception {
        int databaseSizeBeforeUpdate = urlParameterRepository.findAll().size();

        // Create the UrlParameter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUrlParameterMockMvc.perform(put("/api/url-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urlParameter)))
            .andExpect(status().isCreated());

        // Validate the UrlParameter in the database
        List<UrlParameter> urlParameterList = urlParameterRepository.findAll();
        assertThat(urlParameterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUrlParameter() throws Exception {
        // Initialize the database
        urlParameterService.save(urlParameter);

        int databaseSizeBeforeDelete = urlParameterRepository.findAll().size();

        // Get the urlParameter
        restUrlParameterMockMvc.perform(delete("/api/url-parameters/{id}", urlParameter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UrlParameter> urlParameterList = urlParameterRepository.findAll();
        assertThat(urlParameterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UrlParameter.class);
    }
}
