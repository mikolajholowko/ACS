package app.application.model;

import app.application.model.dto.EntranceDto;
import app.application.model.dto.RoomDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String roomName;
    private boolean active;
    private int roomNumber;
    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Entrance> entrances;


    public static RoomDto mapToDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomName(room.getRoomName());
        roomDto.setRoomNumber(room.getRoomNumber());
        roomDto.setActive(room.isActive());
        return roomDto;
    }

    public static Room mapToEntity(RoomDto roomDto){
        Room room = new Room();
        room.setRoomName(roomDto.getRoomName());
        room.setId(roomDto.getId());
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setActive(roomDto.isActive());
        return room;
    }
}


