package com.highschool.services;

import com.highschool.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User findOneById(Long userId);

    void save(User user);

    //void activeAccount(String codeStr);

    User findByEmail(String email);

    boolean hasRole(String role, User user);

    List<User> getAllUser();

    void delete(Long userId);

    public boolean isEnabled();

}
