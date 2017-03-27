package com.tadpole.northmuse.web.rest;

import com.tadpole.northmuse.NorthMuseApp;

import com.tadpole.northmuse.domain.WebService;
import com.tadpole.northmuse.repository.WebServiceRepository;
import com.tadpole.northmuse.service.WebServiceService;
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

import com.tadpole.northmuse.domain.enumeration.WsHttpMethod;
/**
 * Test class for the WebServiceResource REST controller.
 *
 * @see WebServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NorthMuseApp.class)
public class WebServiceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final WsHttpMethod DEFAULT_METHOD = WsHttpMethod.GET;
    private static final WsHttpMethod UPDATED_METHOD = WsHttpMethod.POST;

    @Autowired
    private WebServiceRepository webServiceRepository;

    @Autowired
    private WebServiceService webServiceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWebServiceMockMvc;

    private WebService webService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WebServiceResource webServiceResource = new WebServiceResource(webServiceService);
        this.restWebServiceMockMvc = MockMvcBuilders.standaloneSetup(webServiceResource)
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
    public static WebService createEntity(EntityManager em) {
        WebService webService = new WebService()
                .name(DEFAULT_NAME)
                .url(DEFAULT_URL)
                .method(DEFAULT_METHOD);
        return webService;
    }

    @Before
    public void initTest() {
        webService = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebService() throws Exception {
        int databaseSizeBeforeCreate = webServiceRepository.findAll().size();

        // Create the WebService

        restWebServiceMockMvc.perform(post("/api/web-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webService)))
            .andExpect(status().isCreated());

        // Validate the WebService in the database
        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeCreate + 1);
        WebService testWebService = webServiceList.get(webServiceList.size() - 1);
        assertThat(testWebService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWebService.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testWebService.getMethod()).isEqualTo(DEFAULT_METHOD);
    }

    @Test
    @Transactional
    public void createWebServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webServiceRepository.findAll().size();

        // Create the WebService with an existing ID
        WebService existingWebService = new WebService();
        existingWebService.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebServiceMockMvc.perform(post("/api/web-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWebService)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = webServiceRepository.findAll().size();
        // set the field null
        webService.setUrl(null);

        // Create the WebService, which fails.

        restWebServiceMockMvc.perform(post("/api/web-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webService)))
            .andExpect(status().isBadRequest());

        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWebServices() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList
        restWebServiceMockMvc.perform(get("/api/web-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())));
    }

    @Test
    @Transactional
    public void getWebService() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get the webService
        restWebServiceMockMvc.perform(get("/api/web-services/{id}", webService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(webService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWebService() throws Exception {
        // Get the webService
        restWebServiceMockMvc.perform(get("/api/web-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebService() throws Exception {
        // Initialize the database
        webServiceService.save(webService);

        int databaseSizeBeforeUpdate = webServiceRepository.findAll().size();

        // Update the webService
        WebService updatedWebService = webServiceRepository.findOne(webService.getId());
        updatedWebService
                .name(UPDATED_NAME)
                .url(UPDATED_URL)
                .method(UPDATED_METHOD);

        restWebServiceMockMvc.perform(put("/api/web-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWebService)))
            .andExpect(status().isOk());

        // Validate the WebService in the database
        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeUpdate);
        WebService testWebService = webServiceList.get(webServiceList.size() - 1);
        assertThat(testWebService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWebService.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testWebService.getMethod()).isEqualTo(UPDATED_METHOD);
    }

    @Test
    @Transactional
    public void updateNonExistingWebService() throws Exception {
        int databaseSizeBeforeUpdate = webServiceRepository.findAll().size();

        // Create the WebService

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWebServiceMockMvc.perform(put("/api/web-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webService)))
            .andExpect(status().isCreated());

        // Validate the WebService in the database
        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWebService() throws Exception {
        // Initialize the database
        webServiceService.save(webService);

        int databaseSizeBeforeDelete = webServiceRepository.findAll().size();

        // Get the webService
        restWebServiceMockMvc.perform(delete("/api/web-services/{id}", webService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebService.class);
    }
}
