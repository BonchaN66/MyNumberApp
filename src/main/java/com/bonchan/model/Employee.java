package com.bonchan.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Employee {

    // ----------------------------
    // 主キー（自動採番）
    // ----------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ----------------------------
    // 従業員コード（ユーザー入力）
    // - DB制約: NOT NULL, UNIQUE
    // - バックエンド: 必須チェック（@NotBlank）
    // ----------------------------
    @Column(nullable = false, unique = true, length = 20)
    @NotBlank(message = "従業員コードは必須です")
    private String employeeCode;

    // ----------------------------
    // 氏名
    // - DB制約: NOT NULL
    // - バックエンド: 必須チェック（@NotBlank）, 最大文字数制限
    // ----------------------------
    @Column(nullable = false, length = 50)
    @NotBlank(message = "氏名は必須です")
    @Size(max = 50, message = "氏名は50文字以内で入力してください")
    private String name;

    // ----------------------------
    // マイナンバー（12桁）
    // - DB制約: UNIQUE, NULL許容
    // - バックエンド: 任意入力、入力があれば12桁チェック
    // ----------------------------
    @Column(unique = true, length = 12)
    @Pattern(regexp = "\\d{12}", message = "マイナンバーは12桁の数字で入力してください")
    private String myNumber;

    // ----------------------------
    // 登録日
    // - DB制約: NULL許容
    // - バックエンド: 自動セット（登録時）
    // ----------------------------
    private LocalDate registeredAt;

    // ----------------------------
    // 備考（任意）
    // - DB制約: 最大255文字
    // - バックエンド: 最大文字数制限
    // ----------------------------
    @Column(length = 255)
    @Size(max = 255, message = "備考は255文字以内で入力してください")
    private String remarks;

    // ---------------------------------------
    // getter / setter
    // ---------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMyNumber() { return myNumber; }
    public void setMyNumber(String myNumber) { this.myNumber = myNumber; }

    public LocalDate getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDate registeredAt) { this.registeredAt = registeredAt; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
