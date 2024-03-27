package org.example.cafeflow.cafe.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CafeRepository {
    private final EntityManager em;

    public void save(Cafe cafe) {
        em.persist(cafe);
    }

    public Cafe findById(Long id) {
        return em.find(Cafe.class, id);
    }

    public List<Cafe> findAll() {
        return em.createQuery("select c from Cafe c", Cafe.class)
                .getResultList();
    }
}
