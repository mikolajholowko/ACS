package app.application.service;

import app.application.model.Entrance;

import app.application.model.dto.EntranceDto;

import app.application.repository.EntranceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EntranceService {

    private final EntranceRepository entranceRepository;

    EntranceDto getById(UUID id) {
        Optional<Entrance> entrance = entranceRepository.findById(id);
        if (entrance.isPresent()) {
            return Entrance.mapToDto(entrance.get());
        } else {
            throw new RuntimeException("User with id: [" + id + "] not found.");
        }
    }

    List<Entrance> getAll() {
        return entranceRepository.findAll();
    }

    void deleteById(UUID id) {
        entranceRepository.findById(id).ifPresent(employee -> entranceRepository.deleteById(id));
    }

    EntranceDto save(Entrance entrance) {
        Entrance save = entranceRepository.save(entrance);
        return Entrance.mapToDto(save);

    }


}
