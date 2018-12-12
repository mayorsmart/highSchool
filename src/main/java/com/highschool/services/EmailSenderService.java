package com.highschool.services;


import com.highschool.entity.User;

public interface EmailSenderService {

    void sendActiveCode(User user);

    void sendResetPasswordCode(User user);
}

