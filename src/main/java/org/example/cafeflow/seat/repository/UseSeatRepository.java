package org.example.cafeflow.seat.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.seat.dto.UseSeat;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UseSeatRepository {
    private final EntityManager em;

    public void save(UseSeat seat) {
        em.persist(seat);
    }
    public UseSeat findById(Long id) {
        return em.find(UseSeat.class, id);
    }

    public UseSeat findBySeatNumber(int seatNumber) {
        return em.createQuery("select u from UseSeat u where u.seatNumber = :seatNumber", UseSeat.class)
                .setParameter("seatNumber", seatNumber)
                .getSingleResult();
    }

    public void delete(Long id) {
        UseSeat seat = findById(id);
        if(seat != null)
            em.remove(seat);
    }
}
