package app.application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class RoomDto {

    private UUID id;
    private String roomName;
    private int roomNumber;
    private int roomAccessibility;
    private boolean active;


    public RoomDto(String roomName, int roomNumber, boolean active, int roomAccessibility) {
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.active = active;
        this.roomAccessibility = roomAccessibility;
    }
}
