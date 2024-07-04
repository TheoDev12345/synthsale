package com.doranco.synthsale.service;

import com.doranco.synthsale.model.User;

public interface UserService {
    void registerUser(User user);
    User findUserById(Long id);
    void deleteUser(Long id);
}
