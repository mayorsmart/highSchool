package com.highschool.securityConfig;

import com.highschool.dao.UserRepository;
import com.highschool.entity.Role;
import com.highschool.entity.User;
import com.highschool.services.UserService;
import com.highschool.services.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(),
                true, true, true, getGrantedAuthorities(user));

    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return authorities;
    }
}

