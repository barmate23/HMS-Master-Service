package com.hotelerp.hotelmaster.repository;

import com.hotelerp.hotelmaster.entity.GstRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GstRuleRepository extends JpaRepository<GstRule, Long> {

    @Query("SELECT g FROM GstRule g WHERE " +
            "(:searchText IS NULL OR LOWER(g.serviceCategory) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(g.hsnSacCode) LIKE LOWER(CONCAT('%', :searchText, '%'))) AND " +
            "(:hotelId IS NULL OR g.hotel.id = :hotelId) AND " +
            "g.isActive = true")
    Page<GstRule> searchGstRules(@Param("searchText") String searchText, @Param("hotelId") Long hotelId, Pageable pageable);

    boolean existsByServiceCategoryIgnoreCaseAndHotelIdAndIsActiveTrue(String serviceCategory, Long hotelId);
}
