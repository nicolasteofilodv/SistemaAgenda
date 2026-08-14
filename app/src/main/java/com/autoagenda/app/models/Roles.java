package com.autoagenda.app.models;

import java.util.Set;
import com.autoagenda.app.utils.UserRoles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Atribuicoes")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
   
    @Column(name = "atribuicao")
    private UserRoles role;
   
    @OneToMany(mappedBy="role")
    private Set<RolesUsers> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public Set<RolesUsers> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolesUsers> roles) {
        this.roles = roles;
    }

    
}
