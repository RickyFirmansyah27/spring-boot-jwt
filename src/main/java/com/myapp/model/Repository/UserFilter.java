package com.myapp.model.Repository;

import com.myapp.model.Entity.User;

import java.util.List;

public interface UserFilter {
    List<User> findUserByCriteria(String name, String email, Integer id, int page, int size);
}
