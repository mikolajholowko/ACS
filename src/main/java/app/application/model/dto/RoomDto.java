package app.application.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class RoomDto {

    private UUID id;
    private String roomName;
    private int roomNumber;
    private boolean active;


}
