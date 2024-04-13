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

    public List<Cafe> findByName(String name){
        return em.createQuery("select c from Cafe c where c.name = :name", Cafe.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Cafe findById(Long id) {
        return em.find(Cafe.class, id);
    }

    public List<Cafe> findAll() {
        return em.createQuery("select c from Cafe c", Cafe.class)
                .getResultList();
    }

    //등록 순 조회
    public List<Cafe> findAllByCreatedAt() {
        return em.createQuery("select c from Cafe c order by c.createdAt", Cafe.class)
                .getResultList();
    }

    //리뷰 갯수순 조회
    public List<Cafe> findAllByReviewsCount() {
        return em.createQuery("select c from Cafe c order by c.reviewsCount desc", Cafe.class)
                .getResultList();
    }

    //리뷰 평점순 조회
    public List<Cafe> findAllByReviewsRating() {
        return em.createQuery("select c from Cafe c order by c.reviewsRating desc", Cafe.class)
                .getResultList();
    }

    public void delete(Long id) {
        Cafe cafe = findById(id);
        if(cafe != null)
            em.remove(cafe);
    }


}
