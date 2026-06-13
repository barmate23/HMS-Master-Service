package com.hotelerp.hotelmaster.repository;

import com.hotelerp.common.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h WHERE " +
            "(:searchText IS NULL OR h.name LIKE %:searchText%) AND " +
            "h.isActive = true")
    Page<Hotel> searchHotels(@Param("searchText") String searchText, Pageable pageable);
}
