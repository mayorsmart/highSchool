package com.highschool.services;

import com.highschool.entity.Code;
import com.highschool.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CodeService {
    List<Code> findByCodeTypeAndUser(int codeType, User user);

    void save(Code code);

    Code findByCodeStr(String codeStr);

    void delete(Code code);
}
