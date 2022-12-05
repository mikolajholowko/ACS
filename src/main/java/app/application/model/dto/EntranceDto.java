package app.application.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class EntranceDto {

    private UUID id;
    LocalDateTime enter;
    LocalDateTime leave;
    Long roomsID;

}
