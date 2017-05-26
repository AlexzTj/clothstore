package com.ecommerce.service;

import com.ecommerce.model.User;


public interface UserService {
    User getUserById(String id);

    void registerUser(User user);

    void deleteUser(String id);

    void updateUser(User user);
}
