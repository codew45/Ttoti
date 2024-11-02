package kr.co.ttoti.backend.domain.room.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ttoti.backend.domain.room.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

	Optional<Room> findByRoomId(Integer roomId);

	Optional<Room> findByRoomCode(String roomCode);

	Optional<Room> findByRoomIdAndRoomIsDeletedFalse(Integer roomId);

	Optional<Room> findByRoomIdAndRoomIsStartedFalse(Integer roomId);
}