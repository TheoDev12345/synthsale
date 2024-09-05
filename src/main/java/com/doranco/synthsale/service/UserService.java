package com.doranco.synthsale.service;

import com.doranco.synthsale.model.User;

public interface UserService {
    void registerUser(User user);
    User findUserById(Long id);
    void deleteUser(Long id);
    
    User findUserByUsername(String username);
    boolean authenticateUser(String username, String password);
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User getLoggedUser();
}

