package com.sporty_shoes.service;

import java.util.Optional;

import com.sporty_shoes.model.CustomUserDetails;
import com.sporty_shoes.model.User;
import com.sporty_shoes.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findUserByEmail(email);
        user.orElseThrow(()-> new UsernameNotFoundException("user not found"));
        return user.map( CustomUserDetails::new).get();
    }
    
}
