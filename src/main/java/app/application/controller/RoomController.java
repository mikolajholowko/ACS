package app.application.controller;
import app.application.model.dto.RoomDto;
import app.application.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor

public class RoomController {

    private final RoomService roomService;


    @GetMapping(value = "/rooms")
    public List<RoomDto> getAllRooms() {
        return roomService.getAll();
    }

    @PostMapping(value = "/room")
    public void save(@RequestBody RoomDto roomDto){
     roomService.save(roomDto);
    }


    @DeleteMapping(value = "/room")
    public void delete(@RequestBody RoomDto roomDto){
     roomService.save(roomDto);
    }



}
