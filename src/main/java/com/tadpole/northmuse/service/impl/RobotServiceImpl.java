package com.tadpole.northmuse.service.impl;

import com.tadpole.northmuse.service.RobotService;
import com.tadpole.northmuse.domain.Robot;
import com.tadpole.northmuse.repository.RobotRepository;
import com.tadpole.northmuse.util.HttpUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Service Implementation for managing Robot.
 */
@Service
@Transactional
public class RobotServiceImpl implements RobotService{

    private final Logger log = LoggerFactory.getLogger(RobotServiceImpl.class);

    private final RobotRepository robotRepository;

    public RobotServiceImpl(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }

    /**
     * Save a robot.
     *
     * @param robot the entity to save
     * @return the persisted entity
     */
    @Override
    public Robot save(Robot robot) {
        log.debug("Request to save Robot : {}", robot);
        Robot result = robotRepository.save(robot);
        return result;
    }

    /**
     *  Get all the robots.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Robot> findAll(Pageable pageable) {
        log.debug("Request to get all Robots");
        Page<Robot> result = robotRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one robot by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Robot findOne(Long id) {
        log.debug("Request to get Robot : {}", id);
        Robot robot = robotRepository.findOne(id);
        return robot;
    }

    /**
     *  Delete the  robot by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Robot : {}", id);
        robotRepository.delete(id);
    }

    @Override
    public String start(Long id) {

        Robot robot = findOne(id);
        try {
           return HttpUtils.newWebClient().getPage(robot.getWebSiteUrl().getFullAddress()).getWebResponse().getContentAsString();

        } catch (IOException e) {

            return e.getMessage();
        }
    }
}
