package com.example.nbcnewsfeed.post.repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;

public class PostRepositoryImpl implements PostRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;

    public PostRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void enableSoftDeleteFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("activePostFilter");
    }
}
