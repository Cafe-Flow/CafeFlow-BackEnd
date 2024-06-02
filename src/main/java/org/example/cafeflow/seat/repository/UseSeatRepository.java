package org.example.cafeflow.seat.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.cafeflow.seat.domain.UseSeat;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public UseSeat findBySeatNumber(Long cafeId, int seatNumber) {
        return em.createQuery("select u from UseSeat u where u.cafe.id = :cafeId and u.seatNumber = :seatNumber", UseSeat.class)
                .setParameter("cafeId", cafeId)
                .setParameter("seatNumber", seatNumber)
                .getSingleResult();
    }

    public int findUsingSeatNumber(Long cafeId) {
        List<UseSeat> useSeats = em.createQuery("select u from UseSeat u where u.cafe.id = :cafeId", UseSeat.class)
                .setParameter("cafeId", cafeId)
                .getResultList();
        return useSeats.size();

    }

    public void delete(Long id) {
        UseSeat seat = findById(id);
        if(seat != null)
            em.remove(seat);
    }

}
