package com.bonchan.service;

import com.bonchan.model.Employee;
import com.bonchan.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * EmployeeService の単体テストクラス
 *
 * Repository をモック化し、Service 単体の振る舞いを検証する。
 * コメントは「前準備 / 実行 / 確認」（Given / When / Then）の形式で整理。
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    // ----------------------------------------------
    // 正常系テスト
    // ----------------------------------------------

    /**
     * [一覧取得] findAll() で社員リストが返ることを確認するテスト
     */
    @Test
    public void testGetAllEmployees() {
        // 前準備 (Given): テスト用の社員データとモックの戻り値を設定
        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setName("佐藤");
        emp1.setMyNumber("123456789012");

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setName("鈴木");
        emp2.setMyNumber("987654321098");

        List<Employee> mockList = List.of(emp1, emp2);
        when(repository.findAll()).thenReturn(mockList);

        // 実行 (When): getAllEmployees() を呼び出し
        List<Employee> result = service.getAllEmployees();

        // 確認 (Then): 件数と内容が期待通りであること
        assertEquals(2, result.size());
        assertEquals("佐藤", result.get(0).getName());
    }

    /**
     * [個別取得] findById() で該当の社員が取得できることを確認するテスト
     */
    @Test
    public void testFindById_found() {
        // 前準備 (Given): 存在する社員を設定
        Employee emp = new Employee();
        emp.setId(1L);
        emp.setName("田中");
        emp.setMyNumber("111111111111");
        when(repository.findById(1L)).thenReturn(Optional.of(emp));

        // 実行 (When)
        Employee result = service.findById(1L);

        // 確認 (Then)
        assertNotNull(result);
        assertEquals("田中", result.getName());
    }

    /**
     * [登録成功] register() が true を返し、
     * registeredAt がセットされ save() が呼ばれることを確認するテスト
     */
    @Test
    public void testRegister_success_setsRegisteredAt_andSaves() {
        // 前準備 (Given): 重複が無い状態を設定
        Employee emp = new Employee();
        emp.setEmployeeCode("E100");
        emp.setName("山田太郎");
        emp.setMyNumber(""); // 未入力扱い
        when(repository.existsByEmployeeCode("E100")).thenReturn(false);

        // 実行 (When)
        boolean result = service.register(emp);

        // 確認 (Then)
        assertTrue(result);
        assertNotNull(emp.getRegisteredAt());
        verify(repository, times(1)).save(emp);
    }

    /**
     * [登録失敗] 従業員コードが重複している場合、false を返し save() は呼ばれない
     */
    @Test
    public void testRegister_duplicateEmployeeCode_returnsFalse_andNoSave() {
        // 前準備 (Given)
        Employee emp = new Employee();
        emp.setEmployeeCode("E001");
        emp.setName("山田");
        when(repository.existsByEmployeeCode("E001")).thenReturn(true);

        // 実行 (When)
        boolean result = service.register(emp);

        // 確認 (Then)
        assertFalse(result);
        verify(repository, never()).save(any());
    }

    /**
     * [登録失敗] マイナンバーが重複している場合、false を返し save() は呼ばれない
     */
    @Test
    public void testRegister_duplicateMyNumber_returnsFalse_andNoSave() {
        // 前準備 (Given)
        Employee emp = new Employee();
        emp.setEmployeeCode("E002");
        emp.setName("佐藤");
        emp.setMyNumber("123456789012");
        when(repository.existsByEmployeeCode("E002")).thenReturn(false);
        when(repository.existsByMyNumber("123456789012")).thenReturn(true);

        // 実行 (When)
        boolean result = service.register(emp);

        // 確認 (Then)
        assertFalse(result);
        verify(repository, never()).save(any());
    }

    /**
     * [削除処理] deleteById() でリポジトリの deleteById() が呼ばれることを確認するテスト
     */
    @Test
    public void testDeleteById_callsDelete() {
        // 前準備 (Given)
        Long id = 1L;

        // 実行 (When)
        service.deleteById(id);

        // 確認 (Then)
        verify(repository, times(1)).deleteById(id);
    }

    /**
     * [更新成功] updateEmployee() で既存社員が正しく更新され OK を返すことを確認するテスト
     */
    @Test
    public void testUpdateEmployee_success() {
        // 前準備 (Given)
        Employee existing = new Employee();
        existing.setId(1L);
        existing.setName("旧名");
        existing.setMyNumber("000000000000");
        existing.setEmployeeCode("E001");

        Employee updated = new Employee();
        updated.setId(1L);
        updated.setName("新名");
        updated.setMyNumber("123456789012");
        updated.setEmployeeCode("E002");
        updated.setRemarks("備考更新");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Employee.class))).thenReturn(updated);

        // 実行 (When)
        String status = service.updateEmployee(updated);

        // 確認 (Then)
        assertEquals("OK", status);
        assertEquals("新名", existing.getName());
        assertEquals("123456789012", existing.getMyNumber());
        assertEquals("E002", existing.getEmployeeCode());
        assertEquals("備考更新", existing.getRemarks());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(existing);
    }

    // ----------------------------------------------
    // 異常系テスト
    // ----------------------------------------------

    /**
     * [個別取得失敗] findById() で存在しない ID を検索すると null が返ることを確認するテスト
     */
    @Test
    public void testFindById_notFound() {
        // 前準備 (Given)
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // 実行 (When)
        Employee result = service.findById(1L);

        // 確認 (Then)
        assertNull(result);
    }

    /**
     * [更新失敗] 対象が存在しない場合、NOT_FOUND を返し save() が呼ばれないことを確認するテスト
     */
    @Test
    public void testUpdateEmployee_notFound_returnsStatus_andNoSave() {
        // 前準備 (Given)
        Employee updated = new Employee();
        updated.setId(1L);
        updated.setName("新名");
        updated.setMyNumber("123456789012");
        updated.setEmployeeCode("E002");
        updated.setRemarks("備考更新");
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // 実行 (When)
        String status = service.updateEmployee(updated);

        // 確認 (Then)
        assertEquals("NOT_FOUND", status);
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any());
    }

    /**
     * [更新失敗] 従業員コードが自分以外と重複する場合、DUPLICATE_CODE を返し保存しないことを確認するテスト
     */
    @Test
    public void testUpdateEmployee_duplicateCode_returnsStatus_andNoSave() {
        // 前準備 (Given)
        Employee existing = new Employee();
        existing.setId(1L);
        existing.setEmployeeCode("E001");

        Employee updated = new Employee();
        updated.setId(1L);
        updated.setEmployeeCode("E999");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.existsByEmployeeCodeAndIdNot("E999", 1L)).thenReturn(true);

        // 実行 (When)
        String status = service.updateEmployee(updated);

        // 確認 (Then)
        assertEquals("DUPLICATE_CODE", status);
        verify(repository, never()).save(any());
    }

    /**
     * [更新失敗] マイナンバーが自分以外と重複する場合、DUPLICATE_MYNUMBER を返し保存しないことを確認するテスト
     */
    @Test
    public void testUpdateEmployee_duplicateMyNumber_returnsStatus_andNoSave() {
        // 前準備 (Given)
        Employee existing = new Employee();
        existing.setId(1L);
        existing.setMyNumber("000000000000");

        Employee updated = new Employee();
        updated.setId(1L);
        updated.setMyNumber("123456789012");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.existsByEmployeeCodeAndIdNot(any(), anyLong())).thenReturn(false);
        when(repository.existsByMyNumberAndIdNot("123456789012", 1L)).thenReturn(true);

        // 実行 (When)
        String status = service.updateEmployee(updated);

        // 確認 (Then)
        assertEquals("DUPLICATE_MYNUMBER", status);
        verify(repository, never()).save(any());
    }
}
