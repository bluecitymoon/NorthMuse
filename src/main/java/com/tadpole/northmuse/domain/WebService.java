package com.tadpole.northmuse.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.tadpole.northmuse.domain.enumeration.WsHttpMethod;

/**
 * A WebService.
 */
@Entity
@Table(name = "web_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WebService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 0, max = 5000)
    @Column(name = "url", length = 5000, nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private WsHttpMethod method;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public WebService name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public WebService url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WsHttpMethod getMethod() {
        return method;
    }

    public WebService method(WsHttpMethod method) {
        this.method = method;
        return this;
    }

    public void setMethod(WsHttpMethod method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebService webService = (WebService) o;
        if (webService.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, webService.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WebService{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", url='" + url + "'" +
            ", method='" + method + "'" +
            '}';
    }
}
