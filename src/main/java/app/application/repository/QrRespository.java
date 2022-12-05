package app.application.repository;


import app.application.model.Qr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrRespository extends JpaRepository<Qr, UUID> {


}
