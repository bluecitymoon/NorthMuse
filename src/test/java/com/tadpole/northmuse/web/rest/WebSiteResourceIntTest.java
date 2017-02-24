package com.tadpole.northmuse.web.rest;

import com.tadpole.northmuse.NorthMuseApp;

import com.tadpole.northmuse.domain.WebSite;
import com.tadpole.northmuse.repository.WebSiteRepository;
import com.tadpole.northmuse.service.WebSiteService;
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
 * Test class for the WebSiteResource REST controller.
 *
 * @see WebSiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NorthMuseApp.class)
public class WebSiteResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROOT_URL = "AAAAAAAAAA";
    private static final String UPDATED_ROOT_URL = "BBBBBBBBBB";

    @Autowired
    private WebSiteRepository webSiteRepository;

    @Autowired
    private WebSiteService webSiteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWebSiteMockMvc;

    private WebSite webSite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WebSiteResource webSiteResource = new WebSiteResource(webSiteService);
        this.restWebSiteMockMvc = MockMvcBuilders.standaloneSetup(webSiteResource)
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
    public static WebSite createEntity(EntityManager em) {
        WebSite webSite = new WebSite()
                .name(DEFAULT_NAME)
                .rootUrl(DEFAULT_ROOT_URL);
        return webSite;
    }

    @Before
    public void initTest() {
        webSite = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebSite() throws Exception {
        int databaseSizeBeforeCreate = webSiteRepository.findAll().size();

        // Create the WebSite

        restWebSiteMockMvc.perform(post("/api/web-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webSite)))
            .andExpect(status().isCreated());

        // Validate the WebSite in the database
        List<WebSite> webSiteList = webSiteRepository.findAll();
        assertThat(webSiteList).hasSize(databaseSizeBeforeCreate + 1);
        WebSite testWebSite = webSiteList.get(webSiteList.size() - 1);
        assertThat(testWebSite.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWebSite.getRootUrl()).isEqualTo(DEFAULT_ROOT_URL);
    }

    @Test
    @Transactional
    public void createWebSiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webSiteRepository.findAll().size();

        // Create the WebSite with an existing ID
        WebSite existingWebSite = new WebSite();
        existingWebSite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebSiteMockMvc.perform(post("/api/web-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWebSite)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WebSite> webSiteList = webSiteRepository.findAll();
        assertThat(webSiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWebSites() throws Exception {
        // Initialize the database
        webSiteRepository.saveAndFlush(webSite);

        // Get all the webSiteList
        restWebSiteMockMvc.perform(get("/api/web-sites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webSite.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].rootUrl").value(hasItem(DEFAULT_ROOT_URL.toString())));
    }

    @Test
    @Transactional
    public void getWebSite() throws Exception {
        // Initialize the database
        webSiteRepository.saveAndFlush(webSite);

        // Get the webSite
        restWebSiteMockMvc.perform(get("/api/web-sites/{id}", webSite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(webSite.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.rootUrl").value(DEFAULT_ROOT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWebSite() throws Exception {
        // Get the webSite
        restWebSiteMockMvc.perform(get("/api/web-sites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebSite() throws Exception {
        // Initialize the database
        webSiteService.save(webSite);

        int databaseSizeBeforeUpdate = webSiteRepository.findAll().size();

        // Update the webSite
        WebSite updatedWebSite = webSiteRepository.findOne(webSite.getId());
        updatedWebSite
                .name(UPDATED_NAME)
                .rootUrl(UPDATED_ROOT_URL);

        restWebSiteMockMvc.perform(put("/api/web-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWebSite)))
            .andExpect(status().isOk());

        // Validate the WebSite in the database
        List<WebSite> webSiteList = webSiteRepository.findAll();
        assertThat(webSiteList).hasSize(databaseSizeBeforeUpdate);
        WebSite testWebSite = webSiteList.get(webSiteList.size() - 1);
        assertThat(testWebSite.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWebSite.getRootUrl()).isEqualTo(UPDATED_ROOT_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingWebSite() throws Exception {
        int databaseSizeBeforeUpdate = webSiteRepository.findAll().size();

        // Create the WebSite

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWebSiteMockMvc.perform(put("/api/web-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webSite)))
            .andExpect(status().isCreated());

        // Validate the WebSite in the database
        List<WebSite> webSiteList = webSiteRepository.findAll();
        assertThat(webSiteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWebSite() throws Exception {
        // Initialize the database
        webSiteService.save(webSite);

        int databaseSizeBeforeDelete = webSiteRepository.findAll().size();

        // Get the webSite
        restWebSiteMockMvc.perform(delete("/api/web-sites/{id}", webSite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WebSite> webSiteList = webSiteRepository.findAll();
        assertThat(webSiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebSite.class);
    }
}
