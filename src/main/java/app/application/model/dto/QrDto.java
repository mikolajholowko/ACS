package app.application.model.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QrDto {

    private UUID id;
    private UUID employeeId;
    private long timestamp;

    public QrDto(UUID employeeId, long timestamp) {
        this.employeeId = employeeId;
        this.timestamp = timestamp;
    }
}
