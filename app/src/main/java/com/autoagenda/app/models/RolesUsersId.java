package com.autoagenda.app.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RolesUsersId implements Serializable{
    
    @Column(name = "role_id")
    private Long roleId;
    @Column(name="user_id")
    private Long userId;
    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    } 
}
