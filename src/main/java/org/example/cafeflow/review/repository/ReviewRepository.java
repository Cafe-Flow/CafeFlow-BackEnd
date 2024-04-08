package org.example.cafeflow.review.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.review.domain.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }

    public List<Review> findByCafeId(Long cafeId) {
        return em.createQuery("select r from Review r where r.cafe.id = :cafe_id", Review.class)
                .setParameter("cafe_id", cafeId)
                .getResultList();
    }
    public Review findById(Long reviewId) {
        return em.find(Review.class, reviewId);
    }

}