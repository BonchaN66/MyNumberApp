package com.bonchan.service;

import com.bonchan.model.Employee;
import com.bonchan.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 社員関連の業務処理を担当するサービスクラス
 * - 登録、更新、削除、一覧取得などのロジックを実装
 * - 重複チェックや存在確認もここで行う
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    /**
     * 全社員の一覧を取得
     *
     * @return 社員リスト
     */
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    /**
     * 社員を登録する
     * - employeeCode と myNumber の重複をチェック
     * - 登録日時をセット
     *
     * @param employee 登録対象社員
     * @return true: 登録成功 / false: 重複あり
     */
    public boolean register(Employee employee) {
        // employeeCode の重複チェック
        if (repository.existsByEmployeeCode(employee.getEmployeeCode())) {
            return false;
        }

        // myNumber が入力されていれば重複チェック
        String myNumber = employee.getMyNumber();
        if (myNumber != null && !myNumber.isEmpty()) {
            if (repository.existsByMyNumber(myNumber)) {
                return false;
            }
        }

        // 登録日時をセットして保存
        employee.setRegisteredAt(LocalDate.now());
        repository.save(employee);
        return true;
    }

    /**
     * 社員情報を更新する
     * - 更新対象が存在しない場合は "NOT_FOUND"
     * - 重複がある場合は "DUPLICATE_CODE" または "DUPLICATE_MYNUMBER"
     * - 正常更新なら "OK"
     *
     * @param updatedEmployee 更新対象社員
     * @return 更新結果ステータス
     */
    public String updateEmployee(Employee updatedEmployee) {
        // 更新対象の存在確認
        Employee existing = repository.findById(updatedEmployee.getId()).orElse(null);
        if (existing == null) {
            return "NOT_FOUND";
        }

        // employeeCode の重複チェック（自分以外）
        if (repository.existsByEmployeeCodeAndIdNot(updatedEmployee.getEmployeeCode(), updatedEmployee.getId())) {
            return "DUPLICATE_CODE";
        }

        // myNumber の重複チェック（入力がある場合、自分以外）
        String myNumber = updatedEmployee.getMyNumber();
        if (myNumber != null && !myNumber.isEmpty()) {
            if (repository.existsByMyNumberAndIdNot(myNumber, updatedEmployee.getId())) {
                return "DUPLICATE_MYNUMBER";
            }
        }

        // 実際の更新処理（IDは変更しない）
        existing.setEmployeeCode(updatedEmployee.getEmployeeCode());
        existing.setName(updatedEmployee.getName());
        existing.setMyNumber(updatedEmployee.getMyNumber());
        existing.setRemarks(updatedEmployee.getRemarks());

        repository.save(existing);
        return "OK";
    }

    /**
     * 社員を削除する
     *
     * @param id 削除対象社員ID
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * IDから社員を取得
     *
     * @param id 社員ID
     * @return 社員情報（存在しない場合は null）
     */
    public Employee findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
