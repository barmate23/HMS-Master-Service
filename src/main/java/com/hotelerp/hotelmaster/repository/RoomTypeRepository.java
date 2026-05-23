package com.hotelerp.hotelmaster.repository;

import com.hotelerp.hotelmaster.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    @Query("SELECT rt FROM RoomType rt WHERE " +
            "(:searchText IS NULL OR rt.name LIKE %:searchText% OR rt.hotel.name LIKE %:searchText%) AND " +
            "(:hotelId IS NULL OR rt.hotel.id = :hotelId) AND " +
            "rt.isActive = true")
    Page<RoomType> searchRoomTypes(@Param("searchText") String searchText, @Param("hotelId") Long hotelId, Pageable pageable);
}
