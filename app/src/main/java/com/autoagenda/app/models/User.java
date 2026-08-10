package com.autoagenda.app.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;
    @Column(name = "usuario", unique = true)
    private String username;
    @Column(name = "senha")
    private String password;
    @Column(name =  "numero_telefone")
    private String phoneNumber;
    @Column(name = "nome")
    private String name;
    @Column(name = "sobrenome")
    private String lastName;

    @OneToMany(mappedBy = "user")
    private List<RolesUsers> userRoles = new ArrayList<>();
        
    public void setId(Long id) {
        this.id = id;
    }

    public List<RolesUsers> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<RolesUsers> userRoles) {
        this.userRoles = userRoles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (RolesUsers role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getAtribuicao().toString()));
        }
        return roles;
    }

    @Override
    public @Nullable String getPassword() {
        // TODO Auto-generated method stub
        return this.password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.username;
    }


}

