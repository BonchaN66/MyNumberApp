package com.bonchan.repository;

import com.bonchan.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Employee エンティティ用のリポジトリ
 * - CRUDはJpaRepositoryで自動提供
 * - 重複チェック用メソッドをカスタムで定義
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // employeeCode の重複チェック（登録用）
    boolean existsByEmployeeCode(String employeeCode);

    // employeeCode の重複チェック（更新用、自分以外）
    boolean existsByEmployeeCodeAndIdNot(String employeeCode, Long id);

    // myNumber の重複チェック（登録用）
    boolean existsByMyNumber(String myNumber);

    // myNumber の重複チェック（更新用、自分以外）
    boolean existsByMyNumberAndIdNot(String myNumber, Long id);
}
