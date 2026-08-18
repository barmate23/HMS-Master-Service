package com.hotelerp.hotelmaster.repository;

import com.hotelerp.hotelmaster.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE " +
            "(:hotelId IS NULL OR r.floor.hotel.id = :hotelId) AND " +
            "(:searchText IS NULL OR r.roomNumber LIKE %:searchText% OR r.floor.floorNumber LIKE %:searchText% OR r.roomType.name LIKE %:searchText%) AND " +
            "(:statusId IS NULL OR r.status.id = :statusId) AND " +
            "(:floorId IS NULL OR r.floor.id = :floorId) AND " +
            "(:roomTypeId IS NULL OR r.roomType.id = :roomTypeId) AND " +
            "r.isActive = true")
    Page<Room> searchRooms(
            @Param("searchText") String searchText, 
            @Param("statusId") Long statusId, 
            @Param("floorId") Long floorId, 
            @Param("roomTypeId") Long roomTypeId, 
            @Param("hotelId") Long hotelId,
            Pageable pageable);

    @Query("SELECT COUNT(r) FROM Room r WHERE (r.floor.hotel.id = :hotelId OR r.roomType.hotel.id = :hotelId) AND (r.isDeleted = false OR r.isDeleted IS NULL)")
    long countByHotelId(@Param("hotelId") Long hotelId);
}
