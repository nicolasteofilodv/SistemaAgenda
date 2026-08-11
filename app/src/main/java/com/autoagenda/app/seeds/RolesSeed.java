package com.autoagenda.app.seeds;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.autoagenda.app.models.Roles;
import com.autoagenda.app.repositories.RolesRepository;
import com.autoagenda.app.utils.UserRoles;

@Component
public class RolesSeed implements CommandLineRunner{

    private final RolesRepository repo;

    public RolesSeed(RolesRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
       seedDb(); 
    }

    void seedDb(){
        if (!repo.existsByRole(UserRoles.ROLE_USER)){
            Roles role = new Roles();
            role.setRole(UserRoles.ROLE_USER);
            this.repo.save(role);
        }
        if (!repo.existsByRole(UserRoles.ROLE_ADMIN)) {
            Roles role = new Roles();
            role.setRole(UserRoles.ROLE_ADMIN);
            this.repo.save(role);
        }
    }
    
}
