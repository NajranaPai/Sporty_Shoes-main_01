package com.sporty_shoes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sporty_shoes.global.GlobalData;
import com.sporty_shoes.model.Role;
import com.sporty_shoes.model.User;
import com.sporty_shoes.repository.RoleRepository;
import com.sporty_shoes.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/login")
    public String login(){
        GlobalData.cart.clear();
        return "login";
    }

     @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("user") User user, HttpServletRequest request) throws Exception {
        
        String encryptedPassword = this.bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        System.out.println(encryptedPassword);
        List<Role> roles = new ArrayList<>();
        roles.add(this.roleRepository.findById(2).get());
        user.setRoles(roles);
        userRepository.save(user);
        request.login(user.getEmail(), user.getPassword());
        return "redirect:/";
    }
    
}
