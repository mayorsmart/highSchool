package com.highschool.services.serviceImp;

import com.highschool.dao.RoleRepository;
import com.highschool.dao.UserRepository;
import com.highschool.entity.Code;
import com.highschool.entity.Role;
import com.highschool.entity.User;
import com.highschool.services.CodeService;
import com.highschool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CodeService codeService;


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


            user.setEnabled(true);
            userRepository.save(user);
            // save role
            roleRepository.save(role);
            // generate active code
            Code code = new Code();
            code.setCodeDate(new Date());
            code.setCodeType(0);
            code.setUser(user);

            codeService.save(code);
        }else{
            userRepository.save(user);
        }
    }

    public void activeAccount(String codeStr){
        Code code = codeService.findByCodeStr(codeStr);
        if(code != null){
            User user = code.getUser();
            Role role = new Role();
            role.setAuthority("ROLE_USER");
            role.setUser(user);
            role.setEmail(user.getEmail());
            roleRepository.save(role);
            // delete role UNAUTH
            roleRepository.delete(roleRepository.findByAuthorityAndUser("ROLE_UNAUTH", user));
            // delete active code
            codeService.delete(code);
        }
    }

    @Override
    public User findOneByUserId(Long userId) {
        return userRepository.findOneByUserId(userId);
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
        userRepository.deleteByUserId(userId);

    }

    @Override
    public boolean isEnabled(){
        return false;
    }


}