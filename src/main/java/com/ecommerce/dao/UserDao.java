package com.ecommerce.dao;


import com.ecommerce.model.User;

public interface UserDao {
    User getUserById(String id);

    void registerUser(User user);

    void deleteUser(String id);

    void updateUser(User user);
}
