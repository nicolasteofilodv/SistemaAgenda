package com.autoagenda.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autoagenda.app.models.RolesUsers;
import com.autoagenda.app.models.RolesUsersId;

public interface RolesUsersRepository extends JpaRepository<RolesUsers, RolesUsersId>{
    
}
