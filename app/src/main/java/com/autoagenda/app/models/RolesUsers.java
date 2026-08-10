package com.autoagenda.app.models;

import com.autoagenda.app.utils.UserRoles;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_roles")
public class RolesUsers {

    @EmbeddedId
    private RolesUsersId id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Roles role;

    @ManyToOne 
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    private UserRoles atribuicao;

    public UserRoles getAtribuicao() {
        return atribuicao;
    }

    public void setAtribuicao(UserRoles atribuicao) {
        this.atribuicao = atribuicao;
    }

    public RolesUsersId getId() {
        return id;
    }

    public void setId(RolesUsersId id) {
        this.id = id;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
