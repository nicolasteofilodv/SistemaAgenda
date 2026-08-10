package com.autoagenda.app.models;

import com.autoagenda.app.utils.UserRoles;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Atribuicoes")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    public Long id;
   
    @Column(name = "atribuicao")
    public UserRoles role = UserRoles.USER;
    @Column(name = "tenant_id")
    public Long tenantId;
    @Column(name = "user_id")
    public Long userId;
    
}
