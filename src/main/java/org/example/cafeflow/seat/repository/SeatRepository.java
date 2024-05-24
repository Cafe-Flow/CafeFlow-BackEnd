package org.example.cafeflow.seat.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
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

    public List<Seat> findAll(Long cafeId) {
        return em.createQuery("select s from Seat s where s.cafe.id = :cafe_id", Seat.class)
                .setParameter("cafe_id", cafeId)
                .getResultList();
    }

    public void removeAll(Long cafeId) {
        em.createQuery("delete from Seat s where s.cafe.id = :cafe_id")
                .setParameter("cafe_id", cafeId)
                .executeUpdate();
    }

    public Seat findBySeatNumber(Long cafeId, int seatNumber) {
        return em.createQuery("select s from Seat s where s.cafe.id = :cafe_id and s.seatNumber = :seat_number", Seat.class)
                .setParameter("cafe_id", cafeId)
                .setParameter("seat_number", seatNumber)
                .getSingleResult();
    }
}