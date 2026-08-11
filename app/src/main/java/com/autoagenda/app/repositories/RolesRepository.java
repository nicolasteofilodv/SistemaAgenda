package com.autoagenda.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autoagenda.app.models.Roles;
import com.autoagenda.app.utils.UserRoles;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    boolean existsByRole(UserRoles role);
}
