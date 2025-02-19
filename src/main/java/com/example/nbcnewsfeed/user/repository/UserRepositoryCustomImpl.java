package com.example.nbcnewsfeed.user.repository;

import com.example.nbcnewsfeed.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    // 아이디, 닉네임으로 사용자 조회
    @Override
    public List<User> findUsers(Long id, String nickname) {

        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("activeUserFilter");

        String jpql = "SELECT u FROM User u WHERE " +
                "(:id IS NULL OR u.id = :id) AND " +
                "(:nickname IS NULL OR u.nickname LIKE CONCAT('%', :nickname, '%'))";
        return entityManager.createQuery(jpql, User.class)
                .setParameter("id", id)
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
