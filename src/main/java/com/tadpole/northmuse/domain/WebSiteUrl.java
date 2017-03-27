package com.tadpole.northmuse.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A WebSiteUrl.
 */
@Entity
@Table(name = "web_site_url")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WebSiteUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "root_address")
    private String rootAddress;

    @Column(name = "full_address", length = 5000)
    private String fullAddress;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private WebSite webSite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRootAddress() {
        return rootAddress;
    }

    public WebSiteUrl rootAddress(String rootAddress) {
        this.rootAddress = rootAddress;
        return this;
    }

    public void setRootAddress(String rootAddress) {
        this.rootAddress = rootAddress;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public WebSiteUrl fullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
        return this;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getName() {
        return name;
    }

    public WebSiteUrl name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WebSite getWebSite() {
        return webSite;
    }

    public WebSiteUrl webSite(WebSite webSite) {
        this.webSite = webSite;
        return this;
    }

    public void setWebSite(WebSite webSite) {
        this.webSite = webSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebSiteUrl webSiteUrl = (WebSiteUrl) o;
        if (webSiteUrl.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, webSiteUrl.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WebSiteUrl{" +
            "id=" + id +
            ", rootAddress='" + rootAddress + "'" +
            ", fullAddress='" + fullAddress + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
