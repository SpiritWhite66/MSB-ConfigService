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
 * A UserAuthorized.
 */
@Entity
@Table(name = "user_authorized")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserAuthorized implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user", nullable = false)
    private String user;

    @NotNull
    @Column(name = "id_user_discord", nullable = false)
    private String idUserDiscord;

    @ManyToMany(mappedBy = "userAuthorizeds")
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

    public String getUser() {
        return user;
    }

    public UserAuthorized user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIdUserDiscord() {
        return idUserDiscord;
    }

    public UserAuthorized idUserDiscord(String idUserDiscord) {
        this.idUserDiscord = idUserDiscord;
        return this;
    }

    public void setIdUserDiscord(String idUserDiscord) {
        this.idUserDiscord = idUserDiscord;
    }

    public Set<ConfigCommon> getConfigCommons() {
        return configCommons;
    }

    public UserAuthorized configCommons(Set<ConfigCommon> configCommons) {
        this.configCommons = configCommons;
        return this;
    }

    public UserAuthorized addConfigCommon(ConfigCommon configCommon) {
        this.configCommons.add(configCommon);
        configCommon.getUserAuthorizeds().add(this);
        return this;
    }

    public UserAuthorized removeConfigCommon(ConfigCommon configCommon) {
        this.configCommons.remove(configCommon);
        configCommon.getUserAuthorizeds().remove(this);
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
        if (!(o instanceof UserAuthorized)) {
            return false;
        }
        return id != null && id.equals(((UserAuthorized) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAuthorized{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", idUserDiscord='" + getIdUserDiscord() + "'" +
            "}";
    }
}
