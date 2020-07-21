package com.msb.config.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Alias.
 */
@Entity
@Table(name = "alias")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Alias implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alias")
    private String alias;

    @ManyToMany(mappedBy = "aliases")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ConfigCommon> configCommons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public Alias alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Set<ConfigCommon> getConfigCommons() {
        return configCommons;
    }

    public Alias configCommons(Set<ConfigCommon> configCommons) {
        this.configCommons = configCommons;
        return this;
    }

    public Alias addConfigCommon(ConfigCommon configCommon) {
        this.configCommons.add(configCommon);
        configCommon.getAliases().add(this);
        return this;
    }

    public Alias removeConfigCommon(ConfigCommon configCommon) {
        this.configCommons.remove(configCommon);
        configCommon.getAliases().remove(this);
        return this;
    }

    public void setConfigCommons(Set<ConfigCommon> configCommons) {
        this.configCommons = configCommons;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alias)) {
            return false;
        }
        return id != null && id.equals(((Alias) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alias{" +
            "id=" + getId() +
            ", alias='" + getAlias() + "'" +
            "}";
    }
}
