package app.application.repository;

import app.application.model.Entrance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EntranceRepository extends JpaRepository<Entrance, UUID> {

    @Override
    Optional<Entrance> findById(UUID uuid);

    @Override
    List<Entrance> findAll();

    @Override
    <S extends Entrance> S save(S entity);

    @Override
    void delete(Entrance entity);


}