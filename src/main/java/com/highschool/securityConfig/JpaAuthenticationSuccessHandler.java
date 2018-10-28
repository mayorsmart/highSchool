package com.highschool.securityConfig;


import com.highschool.entity.User;
import com.highschool.services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
public class JpaAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    protected Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
                                        throws IOException, ServletException {
        // Set to session
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        if(userDetails.getUsername() != null){

            // User name
            User user = userService.findByEmail(userDetails.getUsername());
            request.getSession().setAttribute("userName_", user.getUsername());
            request.getSession().setAttribute("userId_", user.getUserId());

        }

        redirectStrategy.sendRedirect(request, response, "/");
        clearAuthenticationAttributes(request);
    }
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
    }
