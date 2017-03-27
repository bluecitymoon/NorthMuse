package com.tadpole.northmuse.web.rest;

import com.tadpole.northmuse.NorthMuseApp;

import com.tadpole.northmuse.domain.Robot;
import com.tadpole.northmuse.repository.RobotRepository;
import com.tadpole.northmuse.service.RobotService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RobotResource REST controller.
 *
 * @see RobotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NorthMuseApp.class)
public class RobotResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_R_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_R_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private RobotService robotService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRobotMockMvc;

    private Robot robot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RobotResource robotResource = new RobotResource(robotService);
        this.restRobotMockMvc = MockMvcBuilders.standaloneSetup(robotResource)
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
    public static Robot createEntity(EntityManager em) {
        Robot robot = new Robot()
                .name(DEFAULT_NAME)
                .rDescription(DEFAULT_R_DESCRIPTION)
                .active(DEFAULT_ACTIVE)
                .createDate(DEFAULT_CREATE_DATE);
        return robot;
    }

    @Before
    public void initTest() {
        robot = createEntity(em);
    }

    @Test
    @Transactional
    public void createRobot() throws Exception {
        int databaseSizeBeforeCreate = robotRepository.findAll().size();

        // Create the Robot

        restRobotMockMvc.perform(post("/api/robots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(robot)))
            .andExpect(status().isCreated());

        // Validate the Robot in the database
        List<Robot> robotList = robotRepository.findAll();
        assertThat(robotList).hasSize(databaseSizeBeforeCreate + 1);
        Robot testRobot = robotList.get(robotList.size() - 1);
        assertThat(testRobot.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRobot.getrDescription()).isEqualTo(DEFAULT_R_DESCRIPTION);
        assertThat(testRobot.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testRobot.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createRobotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = robotRepository.findAll().size();

        // Create the Robot with an existing ID
        Robot existingRobot = new Robot();
        existingRobot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRobotMockMvc.perform(post("/api/robots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRobot)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Robot> robotList = robotRepository.findAll();
        assertThat(robotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRobots() throws Exception {
        // Initialize the database
        robotRepository.saveAndFlush(robot);

        // Get all the robotList
        restRobotMockMvc.perform(get("/api/robots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(robot.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].rDescription").value(hasItem(DEFAULT_R_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getRobot() throws Exception {
        // Initialize the database
        robotRepository.saveAndFlush(robot);

        // Get the robot
        restRobotMockMvc.perform(get("/api/robots/{id}", robot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(robot.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.rDescription").value(DEFAULT_R_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRobot() throws Exception {
        // Get the robot
        restRobotMockMvc.perform(get("/api/robots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRobot() throws Exception {
        // Initialize the database
        robotService.save(robot);

        int databaseSizeBeforeUpdate = robotRepository.findAll().size();

        // Update the robot
        Robot updatedRobot = robotRepository.findOne(robot.getId());
        updatedRobot
                .name(UPDATED_NAME)
                .rDescription(UPDATED_R_DESCRIPTION)
                .active(UPDATED_ACTIVE)
                .createDate(UPDATED_CREATE_DATE);

        restRobotMockMvc.perform(put("/api/robots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRobot)))
            .andExpect(status().isOk());

        // Validate the Robot in the database
        List<Robot> robotList = robotRepository.findAll();
        assertThat(robotList).hasSize(databaseSizeBeforeUpdate);
        Robot testRobot = robotList.get(robotList.size() - 1);
        assertThat(testRobot.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRobot.getrDescription()).isEqualTo(UPDATED_R_DESCRIPTION);
        assertThat(testRobot.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testRobot.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRobot() throws Exception {
        int databaseSizeBeforeUpdate = robotRepository.findAll().size();

        // Create the Robot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRobotMockMvc.perform(put("/api/robots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(robot)))
            .andExpect(status().isCreated());

        // Validate the Robot in the database
        List<Robot> robotList = robotRepository.findAll();
        assertThat(robotList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRobot() throws Exception {
        // Initialize the database
        robotService.save(robot);

        int databaseSizeBeforeDelete = robotRepository.findAll().size();

        // Get the robot
        restRobotMockMvc.perform(delete("/api/robots/{id}", robot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Robot> robotList = robotRepository.findAll();
        assertThat(robotList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Robot.class);
    }
}
