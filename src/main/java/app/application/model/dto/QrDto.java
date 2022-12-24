package app.application.model.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QrDto {

    private UUID id;
    private UUID employeeId;
    private long timestamp;
    private Role role;

    public QrDto(UUID employeeId, long timestamp, Role role) {
        this.employeeId = employeeId;
        this.timestamp = timestamp;
        this.role = role;
    }

}
