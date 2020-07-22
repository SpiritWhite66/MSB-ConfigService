package com.msb.config.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_role_discord", nullable = false)
    private String idRoleDiscord;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
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

    public String getIdRoleDiscord() {
        return idRoleDiscord;
    }

    public Role idRoleDiscord(String idRoleDiscord) {
        this.idRoleDiscord = idRoleDiscord;
        return this;
    }

    public void setIdRoleDiscord(String idRoleDiscord) {
        this.idRoleDiscord = idRoleDiscord;
    }

    public String getName() {
        return name;
    }

    public Role name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ConfigCommon> getConfigCommons() {
        return configCommons;
    }

    public Role configCommons(Set<ConfigCommon> configCommons) {
        this.configCommons = configCommons;
        return this;
    }

    public Role addConfigCommon(ConfigCommon configCommon) {
        this.configCommons.add(configCommon);
        configCommon.getRoles().add(this);
        return this;
    }

    public Role removeConfigCommon(ConfigCommon configCommon) {
        this.configCommons.remove(configCommon);
        configCommon.getRoles().remove(this);
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
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", idRoleDiscord='" + getIdRoleDiscord() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
