package com.highschool.dao;

import com.highschool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findOneByUserId(Long userId);

    void deleteByUserId(Long userId);
}
