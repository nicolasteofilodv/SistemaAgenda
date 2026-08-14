package com.autoagenda.app.services;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.autoagenda.app.dto.user.RegisterRequest;
import com.autoagenda.app.models.AvailableDays;
import com.autoagenda.app.models.RolesUsers;
import com.autoagenda.app.models.RolesUsersId;
import com.autoagenda.app.models.User;
import com.autoagenda.app.repositories.AvaliableDaysRepository;
import com.autoagenda.app.repositories.RolesRepository;
import com.autoagenda.app.repositories.RolesUsersRepository;
import com.autoagenda.app.repositories.UserRepository;
import com.autoagenda.app.utils.UserRoles;


@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final AvaliableDaysRepository avaliableDaysRepository;
    private final RolesRepository rolesRepository;
    private final RolesUsersRepository rolesUsersRepository;

    public UserService(UserRepository userRepository, AvaliableDaysRepository avaliableDaysRepository, RolesRepository rolesRepository, RolesUsersRepository rolesUsersRepository) {
        this.userRepository = userRepository;
        this.avaliableDaysRepository = avaliableDaysRepository;
        this.rolesRepository = rolesRepository;
        this.rolesUsersRepository = rolesUsersRepository;
    }

    public User createUser(RegisterRequest dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
        

        var createdUser = this.userRepository.save(user);
        var roleRef = this.rolesRepository.getReferenceByRole(UserRoles.ROLE_USER).get();
        var userRole = new RolesUsers();
        var idRoleUser = new RolesUsersId();
        
        idRoleUser.setRoleId(roleRef.getId());
        idRoleUser.setUserId(createdUser.getId());
        
        userRole.setId(idRoleUser);
        userRole.setUser(createdUser);
        userRole.setRole(roleRef); 

        this.rolesUsersRepository.save(userRole); 
        setUserDays(createdUser);
        return createdUser;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Email ou senha incorretos, tente novamente");
        }
        var userData = user.get();
        return userData;
    }

    private void setUserDays(User user){
        OffsetTime start = OffsetTime.of(LocalTime.of(8, 0), ZoneOffset.UTC);
        OffsetTime finish = OffsetTime.of(LocalTime.of(17, 0), ZoneOffset.UTC);

        for (DayOfWeek day : DayOfWeek.values()) {
            var ad = new AvailableDays();
            ad.setOpening(start); 
            ad.setClosening(finish);
            ad.setWeekDay(day);
            ad.setUser(user);

            this.avaliableDaysRepository.save(ad);
       }
    }
    
}
