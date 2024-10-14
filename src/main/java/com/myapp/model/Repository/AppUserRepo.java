package com.myapp.model.Repository;

import com.myapp.model.Entity.AppUser;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface AppUserRepo extends CrudRepository<AppUser, Long> {
    // You can define custom query methods here if needed
    Optional<AppUser> findByEmail(String email);
}