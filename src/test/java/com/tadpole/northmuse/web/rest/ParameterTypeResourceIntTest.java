package com.tadpole.northmuse.web.rest;

import com.tadpole.northmuse.NorthMuseApp;

import com.tadpole.northmuse.domain.ParameterType;
import com.tadpole.northmuse.repository.ParameterTypeRepository;
import com.tadpole.northmuse.service.ParameterTypeService;
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
 * Test class for the ParameterTypeResource REST controller.
 *
 * @see ParameterTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NorthMuseApp.class)
public class ParameterTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private ParameterTypeRepository parameterTypeRepository;

    @Autowired
    private ParameterTypeService parameterTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParameterTypeMockMvc;

    private ParameterType parameterType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParameterTypeResource parameterTypeResource = new ParameterTypeResource(parameterTypeService);
        this.restParameterTypeMockMvc = MockMvcBuilders.standaloneSetup(parameterTypeResource)
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
    public static ParameterType createEntity(EntityManager em) {
        ParameterType parameterType = new ParameterType()
                .name(DEFAULT_NAME)
                .identifier(DEFAULT_IDENTIFIER);
        return parameterType;
    }

    @Before
    public void initTest() {
        parameterType = createEntity(em);
    }

    @Test
    @Transactional
    public void createParameterType() throws Exception {
        int databaseSizeBeforeCreate = parameterTypeRepository.findAll().size();

        // Create the ParameterType

        restParameterTypeMockMvc.perform(post("/api/parameter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parameterType)))
            .andExpect(status().isCreated());

        // Validate the ParameterType in the database
        List<ParameterType> parameterTypeList = parameterTypeRepository.findAll();
        assertThat(parameterTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ParameterType testParameterType = parameterTypeList.get(parameterTypeList.size() - 1);
        assertThat(testParameterType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParameterType.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createParameterTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parameterTypeRepository.findAll().size();

        // Create the ParameterType with an existing ID
        ParameterType existingParameterType = new ParameterType();
        existingParameterType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParameterTypeMockMvc.perform(post("/api/parameter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingParameterType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ParameterType> parameterTypeList = parameterTypeRepository.findAll();
        assertThat(parameterTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParameterTypes() throws Exception {
        // Initialize the database
        parameterTypeRepository.saveAndFlush(parameterType);

        // Get all the parameterTypeList
        restParameterTypeMockMvc.perform(get("/api/parameter-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameterType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())));
    }

    @Test
    @Transactional
    public void getParameterType() throws Exception {
        // Initialize the database
        parameterTypeRepository.saveAndFlush(parameterType);

        // Get the parameterType
        restParameterTypeMockMvc.perform(get("/api/parameter-types/{id}", parameterType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parameterType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParameterType() throws Exception {
        // Get the parameterType
        restParameterTypeMockMvc.perform(get("/api/parameter-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParameterType() throws Exception {
        // Initialize the database
        parameterTypeService.save(parameterType);

        int databaseSizeBeforeUpdate = parameterTypeRepository.findAll().size();

        // Update the parameterType
        ParameterType updatedParameterType = parameterTypeRepository.findOne(parameterType.getId());
        updatedParameterType
                .name(UPDATED_NAME)
                .identifier(UPDATED_IDENTIFIER);

        restParameterTypeMockMvc.perform(put("/api/parameter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParameterType)))
            .andExpect(status().isOk());

        // Validate the ParameterType in the database
        List<ParameterType> parameterTypeList = parameterTypeRepository.findAll();
        assertThat(parameterTypeList).hasSize(databaseSizeBeforeUpdate);
        ParameterType testParameterType = parameterTypeList.get(parameterTypeList.size() - 1);
        assertThat(testParameterType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParameterType.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingParameterType() throws Exception {
        int databaseSizeBeforeUpdate = parameterTypeRepository.findAll().size();

        // Create the ParameterType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParameterTypeMockMvc.perform(put("/api/parameter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parameterType)))
            .andExpect(status().isCreated());

        // Validate the ParameterType in the database
        List<ParameterType> parameterTypeList = parameterTypeRepository.findAll();
        assertThat(parameterTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParameterType() throws Exception {
        // Initialize the database
        parameterTypeService.save(parameterType);

        int databaseSizeBeforeDelete = parameterTypeRepository.findAll().size();

        // Get the parameterType
        restParameterTypeMockMvc.perform(delete("/api/parameter-types/{id}", parameterType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParameterType> parameterTypeList = parameterTypeRepository.findAll();
        assertThat(parameterTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParameterType.class);
    }
}
