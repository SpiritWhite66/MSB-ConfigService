package com.msb.config.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ChannelLinked.
 */
@Entity
@Table(name = "channel_linked")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChannelLinked implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "id_channel_discord")
    private String idChannelDiscord;

    @ManyToMany(mappedBy = "channelLinkeds")
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

    public String getName() {
        return name;
    }

    public ChannelLinked name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdChannelDiscord() {
        return idChannelDiscord;
    }

    public ChannelLinked idChannelDiscord(String idChannelDiscord) {
        this.idChannelDiscord = idChannelDiscord;
        return this;
    }

    public void setIdChannelDiscord(String idChannelDiscord) {
        this.idChannelDiscord = idChannelDiscord;
    }

    public Set<ConfigCommon> getConfigCommons() {
        return configCommons;
    }

    public ChannelLinked configCommons(Set<ConfigCommon> configCommons) {
        this.configCommons = configCommons;
        return this;
    }

    public ChannelLinked addConfigCommon(ConfigCommon configCommon) {
        this.configCommons.add(configCommon);
        configCommon.getChannelLinkeds().add(this);
        return this;
    }

    public ChannelLinked removeConfigCommon(ConfigCommon configCommon) {
        this.configCommons.remove(configCommon);
        configCommon.getChannelLinkeds().remove(this);
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
        if (!(o instanceof ChannelLinked)) {
            return false;
        }
        return id != null && id.equals(((ChannelLinked) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChannelLinked{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", idChannelDiscord='" + getIdChannelDiscord() + "'" +
            "}";
    }
}
