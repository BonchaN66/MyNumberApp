package com.bonchan.service;

import com.bonchan.model.Employee;
import com.bonchan.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// 業務処理を担当する@Serviceクラス
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    // 社員一覧を取得する
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // 社員登録処理
    public void register(Employee employee) {
        // 登録日時をセット
        employee.setRegisteredAt(LocalDate.now());
        // Repositoryに保存
        repository.save(employee);
    }

    // 社員削除処理
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void updateEmployee(Employee updatedEmployee) {
        Employee existing = repository.findById(updatedEmployee.getId())
                .orElseThrow(() -> new RuntimeException("該当従業員が存在しません"));

        // ID（PK）は触らない。業務項目だけ更新
        existing.setEmployeeCode(updatedEmployee.getEmployeeCode());
        existing.setName(updatedEmployee.getName());
        existing.setMyNumber(updatedEmployee.getMyNumber());
        existing.setRemarks(updatedEmployee.getRemarks());

        repository.save(existing);
    }

    // IDから社員を取得（null返却にしておく）
    public Employee findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
