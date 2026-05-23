package com.hotelerp.hotelmaster.repository;

import com.hotelerp.hotelmaster.entity.RatePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatePlanRepository extends JpaRepository<RatePlan, Long> {

    @Query("SELECT rp FROM RatePlan rp WHERE " +
            "(:searchText IS NULL OR rp.name LIKE %:searchText%) AND " +
            "rp.isActive = true")
    Page<RatePlan> searchRatePlans(@Param("searchText") String searchText, Pageable pageable);
}
