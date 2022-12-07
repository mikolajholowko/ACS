package app.application.model;

import app.application.model.dto.EmployeeDto;
import app.application.model.dto.QrDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "QR")

public class Qr {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id = UUID.randomUUID();
    private byte[] qrImage = new byte[]{};
    private long timestamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    public Qr(byte[] qrImage, long timestamp, Employee employee) {
        this.qrImage = qrImage;
        this.timestamp = timestamp;
        this.employee = employee;
    }


    public static QrDto mapToDto(Qr qr) {
        QrDto qrDto = new QrDto();
        qrDto.setId(qr.getId());
        qrDto.setTimestamp(qr.getTimestamp());
        qrDto.setEmployeeId(qr.getEmployee().getId());
        return qrDto;
    }




}
