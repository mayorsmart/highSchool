package com.highschool.services.serviceImp;

import com.highschool.dao.CodeRepository;
import com.highschool.entity.Code;
import com.highschool.entity.User;
import com.highschool.services.CodeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

public class CodeServiceImp implements CodeService {

    @Autowired
    private CodeRepository codeRepository;

    @Override
    public List<Code> findByCodeTypeAndUser(int codeType, User user) {
        return codeRepository.findByCodeTypeAndCustomer(codeType, user);
    }

    public void save(Code code){
        SecureRandom random = new SecureRandom();
        String codeStr = new BigInteger(130, random).toString(32);
        code.setCodeStr(codeStr);
        codeRepository.save(code);
    }

    @Override
    public Code findByCodeStr(String codeStr) {
       return codeRepository.findByCodeStr(codeStr);
    }

    @Override
    public void delete(Code code) {
        codeRepository.delete(code);
    }
}
