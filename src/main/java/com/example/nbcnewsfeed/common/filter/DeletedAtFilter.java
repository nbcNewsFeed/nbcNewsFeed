package com.example.nbcnewsfeed.common.filter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeletedAtFilter {

    @PersistenceContext
    private final EntityManager entityManager;

    public void enableSoftDeleteFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("activePostFilter");
    }
}
