package com.bonchan.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              // PrimaryKey（主キー）
    private String employeeCode;   // 従業員コード（ユーザー入力）
    private String name;            // 氏名
    private String myNumber;        // マイナンバー（12桁）
    private LocalDate registeredAt; // 登録日
    private String remarks;         // 備考（任意）

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMyNumber() { return myNumber; }
    public void setMyNumber(String myNumber) { this.myNumber = myNumber; }

    public LocalDate getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDate registeredAt) { this.registeredAt = registeredAt; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
