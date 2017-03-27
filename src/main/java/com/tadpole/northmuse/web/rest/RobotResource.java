package com.tadpole.northmuse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tadpole.northmuse.domain.Robot;
import com.tadpole.northmuse.service.RobotService;
import com.tadpole.northmuse.web.rest.util.HeaderUtil;
import com.tadpole.northmuse.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Robot.
 */
@RestController
@RequestMapping("/api")
public class RobotResource {

    private final Logger log = LoggerFactory.getLogger(RobotResource.class);

    private static final String ENTITY_NAME = "robot";

    private final RobotService robotService;

    public RobotResource(RobotService robotService) {
        this.robotService = robotService;
    }

    /**
     * POST  /robots : Create a new robot.
     *
     * @param robot the robot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new robot, or with status 400 (Bad Request) if the robot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/robots")
    @Timed
    public ResponseEntity<Robot> createRobot(@RequestBody Robot robot) throws URISyntaxException {
        log.debug("REST request to save Robot : {}", robot);
        if (robot.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new robot cannot already have an ID")).body(null);
        }
        Robot result = robotService.save(robot);
        return ResponseEntity.created(new URI("/api/robots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /robots : Updates an existing robot.
     *
     * @param robot the robot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated robot,
     * or with status 400 (Bad Request) if the robot is not valid,
     * or with status 500 (Internal Server Error) if the robot couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/robots")
    @Timed
    public ResponseEntity<Robot> updateRobot(@RequestBody Robot robot) throws URISyntaxException {
        log.debug("REST request to update Robot : {}", robot);
        if (robot.getId() == null) {
            return createRobot(robot);
        }
        Robot result = robotService.save(robot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, robot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /robots : get all the robots.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of robots in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/robots")
    @Timed
    public ResponseEntity<List<Robot>> getAllRobots(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Robots");
        Page<Robot> page = robotService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/robots");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /robots/:id : get the "id" robot.
     *
     * @param id the id of the robot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the robot, or with status 404 (Not Found)
     */
    @GetMapping("/robots/{id}")
    @Timed
    public ResponseEntity<Robot> getRobot(@PathVariable Long id) {
        log.debug("REST request to get Robot : {}", id);
        Robot robot = robotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(robot));
    }

    /**
     * GET  /robots/:id : get the "id" robot.
     *
     * @param id the id of the robot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the robot, or with status 404 (Not Found)
     */
    @GetMapping("/robots/start/{id}")
    @Timed
    public ResponseEntity<String> startRobot(@PathVariable Long id) {
        log.debug("REST request to start Robot : {}", id);

        String response = robotService.start(id);

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE  /robots/:id : delete the "id" robot.
     *
     * @param id the id of the robot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/robots/{id}")
    @Timed
    public ResponseEntity<Void> deleteRobot(@PathVariable Long id) {
        log.debug("REST request to delete Robot : {}", id);
        robotService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
