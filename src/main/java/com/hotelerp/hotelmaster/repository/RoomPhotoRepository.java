package com.hotelerp.hotelmaster.repository;

import com.hotelerp.hotelmaster.entity.RoomPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomPhotoRepository extends JpaRepository<RoomPhoto, Long> {
    List<RoomPhoto> findByRoomId(Long roomId);
    void deleteByRoomId(Long roomId);
}
