package org.example.cafeflow.seat.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.cafeflow.review.domain.Review;
import org.example.cafeflow.seat.domain.Seat;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatRepository {
    private final EntityManager em;

    public void save(Seat seat) {
        em.persist(seat);
    }

    public Seat findById(Long id) {
        return em.find(Seat.class, id);
    }

    public List<Seat> findAll() {
        return em.createQuery("select r from Seat s", Seat.class)
                .getResultList();
    }
}