package kr.co.ttoti.backend.domain.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ttoti.backend.domain.room.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

	Room findByRoomIdAndRoomIsDeletedFalse(Integer roomId);
}