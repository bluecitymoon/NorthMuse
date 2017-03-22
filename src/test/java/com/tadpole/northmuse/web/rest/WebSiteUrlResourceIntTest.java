package com.tadpole.northmuse.web.rest;

import com.tadpole.northmuse.NorthMuseApp;

import com.tadpole.northmuse.domain.WebSiteUrl;
import com.tadpole.northmuse.repository.WebSiteUrlRepository;
import com.tadpole.northmuse.service.WebSiteUrlService;
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
 * Test class for the WebSiteUrlResource REST controller.
 *
 * @see WebSiteUrlResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NorthMuseApp.class)
public class WebSiteUrlResourceIntTest {

    private static final String DEFAULT_ROOT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ROOT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FULL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private WebSiteUrlRepository webSiteUrlRepository;

    @Autowired
    private WebSiteUrlService webSiteUrlService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWebSiteUrlMockMvc;

    private WebSiteUrl webSiteUrl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WebSiteUrlResource webSiteUrlResource = new WebSiteUrlResource(webSiteUrlService);
        this.restWebSiteUrlMockMvc = MockMvcBuilders.standaloneSetup(webSiteUrlResource)
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
    public static WebSiteUrl createEntity(EntityManager em) {
        WebSiteUrl webSiteUrl = new WebSiteUrl()
                .rootAddress(DEFAULT_ROOT_ADDRESS)
                .fullAddress(DEFAULT_FULL_ADDRESS)
                .name(DEFAULT_NAME);
        return webSiteUrl;
    }

    @Before
    public void initTest() {
        webSiteUrl = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebSiteUrl() throws Exception {
        int databaseSizeBeforeCreate = webSiteUrlRepository.findAll().size();

        // Create the WebSiteUrl

        restWebSiteUrlMockMvc.perform(post("/api/web-site-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webSiteUrl)))
            .andExpect(status().isCreated());

        // Validate the WebSiteUrl in the database
        List<WebSiteUrl> webSiteUrlList = webSiteUrlRepository.findAll();
        assertThat(webSiteUrlList).hasSize(databaseSizeBeforeCreate + 1);
        WebSiteUrl testWebSiteUrl = webSiteUrlList.get(webSiteUrlList.size() - 1);
        assertThat(testWebSiteUrl.getRootAddress()).isEqualTo(DEFAULT_ROOT_ADDRESS);
        assertThat(testWebSiteUrl.getFullAddress()).isEqualTo(DEFAULT_FULL_ADDRESS);
        assertThat(testWebSiteUrl.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createWebSiteUrlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webSiteUrlRepository.findAll().size();

        // Create the WebSiteUrl with an existing ID
        WebSiteUrl existingWebSiteUrl = new WebSiteUrl();
        existingWebSiteUrl.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebSiteUrlMockMvc.perform(post("/api/web-site-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWebSiteUrl)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WebSiteUrl> webSiteUrlList = webSiteUrlRepository.findAll();
        assertThat(webSiteUrlList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWebSiteUrls() throws Exception {
        // Initialize the database
        webSiteUrlRepository.saveAndFlush(webSiteUrl);

        // Get all the webSiteUrlList
        restWebSiteUrlMockMvc.perform(get("/api/web-site-urls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webSiteUrl.getId().intValue())))
            .andExpect(jsonPath("$.[*].rootAddress").value(hasItem(DEFAULT_ROOT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].fullAddress").value(hasItem(DEFAULT_FULL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getWebSiteUrl() throws Exception {
        // Initialize the database
        webSiteUrlRepository.saveAndFlush(webSiteUrl);

        // Get the webSiteUrl
        restWebSiteUrlMockMvc.perform(get("/api/web-site-urls/{id}", webSiteUrl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(webSiteUrl.getId().intValue()))
            .andExpect(jsonPath("$.rootAddress").value(DEFAULT_ROOT_ADDRESS.toString()))
            .andExpect(jsonPath("$.fullAddress").value(DEFAULT_FULL_ADDRESS.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWebSiteUrl() throws Exception {
        // Get the webSiteUrl
        restWebSiteUrlMockMvc.perform(get("/api/web-site-urls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebSiteUrl() throws Exception {
        // Initialize the database
        webSiteUrlService.save(webSiteUrl);

        int databaseSizeBeforeUpdate = webSiteUrlRepository.findAll().size();

        // Update the webSiteUrl
        WebSiteUrl updatedWebSiteUrl = webSiteUrlRepository.findOne(webSiteUrl.getId());
        updatedWebSiteUrl
                .rootAddress(UPDATED_ROOT_ADDRESS)
                .fullAddress(UPDATED_FULL_ADDRESS)
                .name(UPDATED_NAME);

        restWebSiteUrlMockMvc.perform(put("/api/web-site-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWebSiteUrl)))
            .andExpect(status().isOk());

        // Validate the WebSiteUrl in the database
        List<WebSiteUrl> webSiteUrlList = webSiteUrlRepository.findAll();
        assertThat(webSiteUrlList).hasSize(databaseSizeBeforeUpdate);
        WebSiteUrl testWebSiteUrl = webSiteUrlList.get(webSiteUrlList.size() - 1);
        assertThat(testWebSiteUrl.getRootAddress()).isEqualTo(UPDATED_ROOT_ADDRESS);
        assertThat(testWebSiteUrl.getFullAddress()).isEqualTo(UPDATED_FULL_ADDRESS);
        assertThat(testWebSiteUrl.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingWebSiteUrl() throws Exception {
        int databaseSizeBeforeUpdate = webSiteUrlRepository.findAll().size();

        // Create the WebSiteUrl

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWebSiteUrlMockMvc.perform(put("/api/web-site-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webSiteUrl)))
            .andExpect(status().isCreated());

        // Validate the WebSiteUrl in the database
        List<WebSiteUrl> webSiteUrlList = webSiteUrlRepository.findAll();
        assertThat(webSiteUrlList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWebSiteUrl() throws Exception {
        // Initialize the database
        webSiteUrlService.save(webSiteUrl);

        int databaseSizeBeforeDelete = webSiteUrlRepository.findAll().size();

        // Get the webSiteUrl
        restWebSiteUrlMockMvc.perform(delete("/api/web-site-urls/{id}", webSiteUrl.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WebSiteUrl> webSiteUrlList = webSiteUrlRepository.findAll();
        assertThat(webSiteUrlList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebSiteUrl.class);
    }
}
