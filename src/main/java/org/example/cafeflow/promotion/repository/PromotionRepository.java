package org.example.cafeflow.promotion.repository;

import org.example.cafeflow.promotion.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    /**
     * 가장 최근에 만들어진 순서로 조회
     */
    @Query("SELECT p FROM Promotion p ORDER BY p.createdAt DESC")
    List<Promotion> findAllByCreatedAt();

    /**
     * 진행중인 프로모션만 조회
     */
    @Query("SELECT p FROM Promotion p WHERE p.startDate <= :currentDate AND p.endDate >= :currentDate")
    List<Promotion> findAllByProceeding(@Param("currentDate") LocalDateTime currentDate);

    /**
     * 끝난 프로모션만 조회
     */
    @Query("SELECT p FROM Promotion p WHERE p.endDate < :currentDate")
    List<Promotion> findAllByEnd(@Param("currentDate") LocalDateTime currentDate);

    /**
     * 진행 예정인 프로모션만 조회
     */
    @Query("SELECT p FROM Promotion p WHERE p.startDate > :currentDate")
    List<Promotion> findAllByUpcoming(@Param("currentDate") LocalDateTime currentDate);

    List<Promotion> findByCafeId(Long cafeId);

   // ----------------------------특정 카페 것만 반환 ------------------------
    /**
     * 가장 최근에 만들어진 순서로 조회
     */
    @Query("SELECT p FROM Promotion p WHERE p.cafe.id = :cafeId ORDER BY p.createdAt DESC")
    List<Promotion> findAllByCreatedAtByCafe(@Param("cafeId") Long cafeId);

    /**
     * 진행중인 프로모션만 조회
     */
    @Query("SELECT p FROM Promotion p WHERE p.cafe.id = :cafeId AND p.startDate <= :currentDate AND p.endDate >= :currentDate")
    List<Promotion> findAllByProceedingByCafe(@Param("currentDate") LocalDateTime currentDate, @Param("cafeId") Long cafeId);

    /**
     * 끝난 프로모션만 조회
     */
    @Query("SELECT p FROM Promotion p WHERE p.cafe.id = :cafeId AND p.endDate < :currentDate")
    List<Promotion> findAllByEndByCafe(@Param("currentDate") LocalDateTime currentDate, @Param("cafeId") Long cafeId);

    /**
     * 진행 예정인 프로모션만 조회
     */
    @Query("SELECT p FROM Promotion p WHERE p.cafe.id = :cafeId AND p.startDate > :currentDate")
    List<Promotion> findAllByUpcomingByCafe(@Param("currentDate") LocalDateTime currentDate, @Param("cafeId") Long cafeId);

    /**
     * 특정 카페의 모든 프로모션 조회
     */
    @Query("SELECT p FROM Promotion p WHERE p.cafe.id = :cafeId")
    List<Promotion> findAllByCafe(@Param("cafeId") Long cafeId);


}