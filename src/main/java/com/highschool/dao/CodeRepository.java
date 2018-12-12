package com.highschool.dao;

import com.highschool.entity.Code;
import com.highschool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {

    List<Code> findByCodeTypeAndCustomer(int codeType, User user);

    Code findByCodeStr(String codeStr);
}
