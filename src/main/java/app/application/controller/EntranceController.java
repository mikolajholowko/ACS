package app.application.controller;


import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
public class EntranceController {


    @PostMapping(value = "entrance/{id}")
    public void enter(@PathVariable UUID id){
        System.out.println("entrances employee/entrances/{id}/enter");
    }

    @PutMapping(value = "entrance/{id}")
    public void leave(@PathVariable UUID id){
        System.out.println("entrances employee/entrances/{id}/leave");
    }


}
