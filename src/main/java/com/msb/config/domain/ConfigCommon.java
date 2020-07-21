package com.msb.config.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.msb.config.domain.enumeration.TypeConfig;

/**
 * A ConfigCommon.
 */
@Entity
@Table(name = "config_common")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConfigCommon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_bot", nullable = false)
    private Integer idBot;

    @NotNull
    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeConfig type;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "config_common_alias",
               joinColumns = @JoinColumn(name = "config_common_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "alias_id", referencedColumnName = "id"))
    private Set<Alias> aliases = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "config_common_channel_linked",
               joinColumns = @JoinColumn(name = "config_common_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "channel_linked_id", referencedColumnName = "id"))
    private Set<ChannelLinked> channelLinkeds = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "config_common_user_authorized",
               joinColumns = @JoinColumn(name = "config_common_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_authorized_id", referencedColumnName = "id"))
    private Set<UserAuthorized> userAuthorizeds = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "config_common_role",
               joinColumns = @JoinColumn(name = "config_common_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdBot() {
        return idBot;
    }

    public ConfigCommon idBot(Integer idBot) {
        this.idBot = idBot;
        return this;
    }

    public void setIdBot(Integer idBot) {
        this.idBot = idBot;
    }

    public Boolean isActivated() {
        return activated;
    }

    public ConfigCommon activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public TypeConfig getType() {
        return type;
    }

    public ConfigCommon type(TypeConfig type) {
        this.type = type;
        return this;
    }

    public void setType(TypeConfig type) {
        this.type = type;
    }

    public Set<Alias> getAliases() {
        return aliases;
    }

    public ConfigCommon aliases(Set<Alias> aliases) {
        this.aliases = aliases;
        return this;
    }

    public ConfigCommon addAlias(Alias alias) {
        this.aliases.add(alias);
        alias.getConfigCommons().add(this);
        return this;
    }

    public ConfigCommon removeAlias(Alias alias) {
        this.aliases.remove(alias);
        alias.getConfigCommons().remove(this);
        return this;
    }

    public void setAliases(Set<Alias> aliases) {
        this.aliases = aliases;
    }

    public Set<ChannelLinked> getChannelLinkeds() {
        return channelLinkeds;
    }

    public ConfigCommon channelLinkeds(Set<ChannelLinked> channelLinkeds) {
        this.channelLinkeds = channelLinkeds;
        return this;
    }

    public ConfigCommon addChannelLinked(ChannelLinked channelLinked) {
        this.channelLinkeds.add(channelLinked);
        channelLinked.getConfigCommons().add(this);
        return this;
    }

    public ConfigCommon removeChannelLinked(ChannelLinked channelLinked) {
        this.channelLinkeds.remove(channelLinked);
        channelLinked.getConfigCommons().remove(this);
        return this;
    }

    public void setChannelLinkeds(Set<ChannelLinked> channelLinkeds) {
        this.channelLinkeds = channelLinkeds;
    }

    public Set<UserAuthorized> getUserAuthorizeds() {
        return userAuthorizeds;
    }

    public ConfigCommon userAuthorizeds(Set<UserAuthorized> userAuthorizeds) {
        this.userAuthorizeds = userAuthorizeds;
        return this;
    }

    public ConfigCommon addUserAuthorized(UserAuthorized userAuthorized) {
        this.userAuthorizeds.add(userAuthorized);
        userAuthorized.getConfigCommons().add(this);
        return this;
    }

    public ConfigCommon removeUserAuthorized(UserAuthorized userAuthorized) {
        this.userAuthorizeds.remove(userAuthorized);
        userAuthorized.getConfigCommons().remove(this);
        return this;
    }

    public void setUserAuthorizeds(Set<UserAuthorized> userAuthorizeds) {
        this.userAuthorizeds = userAuthorizeds;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public ConfigCommon roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public ConfigCommon addRole(Role role) {
        this.roles.add(role);
        role.getConfigCommons().add(this);
        return this;
    }

    public ConfigCommon removeRole(Role role) {
        this.roles.remove(role);
        role.getConfigCommons().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigCommon)) {
            return false;
        }
        return id != null && id.equals(((ConfigCommon) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigCommon{" +
            "id=" + getId() +
            ", idBot=" + getIdBot() +
            ", activated='" + isActivated() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
