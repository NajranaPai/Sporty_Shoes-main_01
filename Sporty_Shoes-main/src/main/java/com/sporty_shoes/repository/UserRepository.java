package com.sporty_shoes.repository;

import java.util.Optional;

import com.sporty_shoes.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>  {
    public Optional<User> findUserByEmail(String email);
}