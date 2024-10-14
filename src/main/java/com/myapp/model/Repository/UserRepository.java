package com.myapp.model.Repository;

import com.myapp.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserFilter {
    // Other query methods if needed
}
