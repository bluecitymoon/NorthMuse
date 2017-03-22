package com.tadpole.northmuse.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UrlParameter.
 */
@Entity
@Table(name = "url_parameter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UrlParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "param_key")
    private String paramKey;

    @Column(name = "param_value")
    private String paramValue;

    @Column(name = "default_value")
    private String defaultValue;

    @ManyToOne
    private WebSiteUrl webSiteUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamKey() {
        return paramKey;
    }

    public UrlParameter paramKey(String paramKey) {
        this.paramKey = paramKey;
        return this;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public UrlParameter paramValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public UrlParameter defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public WebSiteUrl getWebSiteUrl() {
        return webSiteUrl;
    }

    public UrlParameter webSiteUrl(WebSiteUrl webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
        return this;
    }

    public void setWebSiteUrl(WebSiteUrl webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UrlParameter urlParameter = (UrlParameter) o;
        if (urlParameter.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, urlParameter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UrlParameter{" +
            "id=" + id +
            ", paramKey='" + paramKey + "'" +
            ", paramValue='" + paramValue + "'" +
            ", defaultValue='" + defaultValue + "'" +
            '}';
    }
}
