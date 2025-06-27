package com.example.MyNumber.model;

import java.time.LocalDate;

public class Employee {
    private String id;              // 社員ID（ユーザー入力）
    private String name;            // 氏名
    private String myNumber;        // マイナンバー（12桁）
    private LocalDate registeredAt; // 登録日
    private String remarks;         // 備考（任意）

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMyNumber() { return myNumber; }
    public void setMyNumber(String myNumber) { this.myNumber = myNumber; }

    public LocalDate getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDate registeredAt) { this.registeredAt = registeredAt; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
