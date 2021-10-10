package com.sporty_shoes.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sporty_shoes.model.Role;
import com.sporty_shoes.model.User;
import com.sporty_shoes.repository.RoleRepository;
import com.sporty_shoes.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler{
 
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    private RedirectStrategy  redirectStrategy = new DefaultRedirectStrategy(); 

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException, ServletException {
           
         OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
         String email = token.getPrincipal().getAttributes().get("email").toString();
         
         // see if the user is not already present
         if(!userRepository.findUserByEmail(email).isPresent()){
             User user = new User();
             // in google given_name = first name and family_name = last name
            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            user.setEmail(email);
            List<Role> roles = new ArrayList<>();
            roles.add(this.roleRepository.findById(2).get());
            user.setRoles(roles);
            userRepository.save(user);
         }

        redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/");
                  
    }
    
}
