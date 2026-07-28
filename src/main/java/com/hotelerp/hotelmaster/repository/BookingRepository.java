package com.hotelerp.hotelmaster.repository;

import com.hotelerp.hotelmaster.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Returns the first active (non-deleted) booking for the given room
     * whose check-out date is today or in the future (i.e. guest is still
     * checked in / upcoming).
     */
    @Query("""
                SELECT b
                FROM Booking b
                JOIN FETCH b.reservation r
                JOIN FETCH r.guest g
                WHERE b.room.id = :roomId
                  AND b.isDeleted = false
                  AND b.checkInDate <= CURRENT_DATE
                  AND b.checkOutDate >= CURRENT_DATE
            """)
    List<Booking> findActiveBookingByRoomId(@Param("roomId") Long roomId);
}
