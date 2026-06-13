package com.hotelerp.hotelmaster.repository;

import com.hotelerp.common.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE " +
            "(:searchText IS NULL OR r.roomNumber LIKE %:searchText% OR r.floor.floorNumber LIKE %:searchText% OR r.roomType.name LIKE %:searchText%) AND " +
            "(:statusId IS NULL OR r.status.id = :statusId) AND " +
            "(:hkStatusId IS NULL OR r.hkStatus.id = :hkStatusId) AND " +
            "(:floorId IS NULL OR r.floor.id = :floorId) AND " +
            "(:roomTypeId IS NULL OR r.roomType.id = :roomTypeId) AND " +
            "r.isActive = true")
    Page<Room> searchRooms(
            @Param("searchText") String searchText, 
            @Param("statusId") Long statusId, 
            @Param("hkStatusId") Long hkStatusId, 
            @Param("floorId") Long floorId, 
            @Param("roomTypeId") Long roomTypeId, 
            Pageable pageable);
}
