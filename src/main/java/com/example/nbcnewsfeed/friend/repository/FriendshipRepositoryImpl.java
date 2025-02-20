package com.example.nbcnewsfeed.friend.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;

public class FriendshipRepositoryImpl implements FriendshipRepositoryCustom{

    @PersistenceContext
    private final EntityManager entityManager;

    public FriendshipRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void enableSoftDeleteFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("activeFriendRequestFilter");
    }

    @Override
    public void enableSoftDeleteFilterV2() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("activeFriendshipFilter");
    }
}
