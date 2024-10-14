package com.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.myapp.model.Entity.User;
import com.myapp.model.Repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Fetch a single user by ID
    public List<User> getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(Collections::singletonList).orElseGet(Collections::emptyList);
    }

    // Update a user
    public User createOrUpdate(User user) {
        return userRepository.save(user);
    }
    

    // Delete a user by ID
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<User> getUsers(int page, int size, String name, String email, Integer id) {
        var user = userRepository.findUserByCriteria(name, email, id, page, size);
        return user;
    }
}
