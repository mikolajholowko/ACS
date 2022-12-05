package app.application.model;


import app.application.model.dto.EmployeeDto;
import app.application.model.dto.EntranceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Entrance {
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
    private LocalDateTime enter;
    private LocalDateTime leave;
    //private Long roomId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public static EntranceDto mapToDto(Entrance entrance) {
        EntranceDto entranceDto = new EntranceDto();
        entranceDto.setId(entrance.getId());
        entranceDto.setEnter(entrance.getEnter());
        entranceDto.setLeave(entrance.getLeave());
        //entranceDto.setRoomsID(entrance.getRoomId());
        return entranceDto;

    }
}
