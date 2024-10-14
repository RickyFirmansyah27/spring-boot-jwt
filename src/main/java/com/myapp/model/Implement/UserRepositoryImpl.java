package com.myapp.model.Implement;

import com.myapp.model.Entity.User;
import com.myapp.model.Repository.UserFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserFilter {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findUserByCriteria(String name, String email, Integer id, int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(userRoot.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (email != null && !email.isEmpty()) {
            predicates.add(cb.like(cb.lower(userRoot.get("email")), "%" + email.toLowerCase() + "%"));
        }
        if (id != null) {
            predicates.add(cb.equal(userRoot.get("id"), id));
        }

        query.select(userRoot).where(predicates.toArray(new Predicate[0]));
        
        // Create TypedQuery and set pagination
        var typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((page - 1) * size); // Offset
        typedQuery.setMaxResults(size); // Limit

        return typedQuery.getResultList();
    }
}
