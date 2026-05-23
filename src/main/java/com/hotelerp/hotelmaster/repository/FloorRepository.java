package com.hotelerp.hotelmaster.repository;

import com.hotelerp.hotelmaster.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
    @Query("SELECT f FROM Floor f WHERE " +
            "(:searchText IS NULL OR f.floorNumber LIKE %:searchText% OR f.hotel.name LIKE %:searchText%) AND " +
            "(:hotelId IS NULL OR f.hotel.id = :hotelId) AND " +
            "f.isActive = true")
    Page<Floor> searchFloors(@Param("searchText") String searchText, @Param("hotelId") Long hotelId, Pageable pageable);
}
