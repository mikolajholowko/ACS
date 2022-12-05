package app.application.repository;

import app.application.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Override
    Optional<Employee> findById(UUID uuid);

    @Override
    List<Employee> findAll();

    @Override
    <S extends Employee> S save(S entity);

    @Override
    void delete(Employee entity);

}