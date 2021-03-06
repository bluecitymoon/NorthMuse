package com.tadpole.northmuse.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.tadpole.northmuse.domain.enumeration.ExecutionWaitType;

/**
 * A Robot.
 */
@Entity
@Table(name = "robot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Robot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "r_description")
    private String rDescription;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "execution_wait_type")
    private ExecutionWaitType executionWaitType;

    @Column(name = "execution_wait_seconds")
    private Integer executionWaitSeconds;

    @ManyToOne
    private WebSiteUrl webSiteUrl;

    @ManyToOne
    private WebService webService;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Robot name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getrDescription() {
        return rDescription;
    }

    public Robot rDescription(String rDescription) {
        this.rDescription = rDescription;
        return this;
    }

    public void setrDescription(String rDescription) {
        this.rDescription = rDescription;
    }

    public Boolean isActive() {
        return active;
    }

    public Robot active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Robot createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public ExecutionWaitType getExecutionWaitType() {
        return executionWaitType;
    }

    public Robot executionWaitType(ExecutionWaitType executionWaitType) {
        this.executionWaitType = executionWaitType;
        return this;
    }

    public void setExecutionWaitType(ExecutionWaitType executionWaitType) {
        this.executionWaitType = executionWaitType;
    }

    public Integer getExecutionWaitSeconds() {
        return executionWaitSeconds;
    }

    public Robot executionWaitSeconds(Integer executionWaitSeconds) {
        this.executionWaitSeconds = executionWaitSeconds;
        return this;
    }

    public void setExecutionWaitSeconds(Integer executionWaitSeconds) {
        this.executionWaitSeconds = executionWaitSeconds;
    }

    public WebSiteUrl getWebSiteUrl() {
        return webSiteUrl;
    }

    public Robot webSiteUrl(WebSiteUrl webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
        return this;
    }

    public void setWebSiteUrl(WebSiteUrl webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }

    public WebService getWebService() {
        return webService;
    }

    public Robot webService(WebService webService) {
        this.webService = webService;
        return this;
    }

    public void setWebService(WebService webService) {
        this.webService = webService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Robot robot = (Robot) o;
        if (robot.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, robot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Robot{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", rDescription='" + rDescription + "'" +
            ", active='" + active + "'" +
            ", createDate='" + createDate + "'" +
            ", executionWaitType='" + executionWaitType + "'" +
            ", executionWaitSeconds='" + executionWaitSeconds + "'" +
            '}';
    }
}
