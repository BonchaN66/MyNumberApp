package com.bonchan.repository;

import com.bonchan.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // findAll, save, deleteById などはJpaRepositoryが自動で提供
}
