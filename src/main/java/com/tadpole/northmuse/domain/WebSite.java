package com.tadpole.northmuse.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A WebSite.
 */
@Entity
@Table(name = "web_site")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WebSite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "root_url")
    private String rootUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public WebSite name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public WebSite rootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
        return this;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebSite webSite = (WebSite) o;
        if (webSite.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, webSite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WebSite{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", rootUrl='" + rootUrl + "'" +
            '}';
    }
}
