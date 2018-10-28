package com.highschool.dao;

import com.highschool.entity.Role;
import com.highschool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthorityAndUser(String auth, User user);
}
