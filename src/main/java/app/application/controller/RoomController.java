package app.application.controller;

import app.application.model.dto.RoomDto;
import app.application.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor

@Slf4j
public class RoomController {

    private final RoomService roomService;


    @GetMapping(value = "/rooms")
    public List<RoomDto> getAllRooms() {
        return roomService.getAll();
    }

    @PostMapping(value = "/room")
    public void save(@RequestBody RoomDto roomDto) {
        roomService.save(roomDto);
    }


    @DeleteMapping(value = "/room")
    public void delete(@RequestBody RoomDto roomDto) {
//        roomDto.getRoomName().split("")
        roomService.save(roomDto);
    }

    @GetMapping(value = "/rooms/access/{id}")
    public List<RoomDto> getAllByEmployeeId(@PathVariable UUID id) {
        List<RoomDto> allByEmployeeId = roomService.getAllByEmployeeId(id);
        log.info(allByEmployeeId.toString());
        return allByEmployeeId;
    }


}
