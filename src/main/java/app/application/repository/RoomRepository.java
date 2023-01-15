package app.application.repository;

import app.application.model.Employee;
import app.application.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    @Override
    Optional<Room> findById(UUID uuid);

    @Override
    List<Room> findAll();

    @Override
    <S extends Room> S save(S entity);

    @Override
    void delete(Room entity);

    @Query(value = "SELECT r from Room r WHERE r.roomName = ?1")
    Optional<Room> findByRoomName(String roomName);

}