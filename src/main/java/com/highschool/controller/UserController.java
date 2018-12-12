package com.highschool.controller;

import com.highschool.dao.RoleRepository;
import com.highschool.dao.UserRepository;
import com.highschool.entity.User;
import com.highschool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;


    public void setToSession(HttpServletRequest request, User user){
        request.getSession().setAttribute("userName_", user.getUsername());
        request.getSession().setAttribute("userId_", user.getUserId());
    }

    @RequestMapping(value = "/rp", method = RequestMethod.POST)
    public String resetPasswordPost(@Valid @ModelAttribute("user") User user, HttpServletRequest request, Model model) {

        User user1 = userService.findByEmail(user.getEmail());
        user.setPassword(user.getPassword());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user1.getEmail(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        setToSession(request, user1);

        userService.save(user1);
        // delete reset password code in DB
        List<Code> codes = codeRepository.findByCodeTypeAndCustomer(1, user1);
        codeRepository.delete(codes);

        model.addAttribute("title", "Password Reset");
        model.addAttribute("msg", "Your password has been reset!");
        return "processSuccess";
    }

}
