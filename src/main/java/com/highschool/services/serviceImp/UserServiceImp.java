package com.highschool.services.serviceImp;

import com.highschool.dao.RoleRepository;
import com.highschool.dao.UserRepository;
import com.highschool.entity.Role;
import com.highschool.entity.User;
import com.highschool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;



    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // save or update
        if(user.getUserId() == null){
            Role role = new Role();
            role.setEmail(user.getEmail());
            role.setAuthority("ROLE_UNAUTH");
            role.setUser(user);

            // create user first
            userRepository.save(user);

            // save role
            roleRepository.save(role);
             }

    }

    @Override
    public User findOneById(Long userId) {
        return userRepository.findOneById(userId);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean hasRole(String role, User user) {
        return (roleRepository.findByAuthorityAndUser(role, user) !=null);
    }


    @Override
    public List<User> getAllUser() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);

    }

    @Override
    public boolean isEnabled(){
        return false;
    }


}