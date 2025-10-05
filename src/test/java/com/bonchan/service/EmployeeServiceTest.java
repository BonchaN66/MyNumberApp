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
 * EmployeeService の単体テスト
 * - Repository をモック化し、Service単体のロジックを検証
 * - コメント形式: 前準備 / 実行 / 確認（Given / When / Then）
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    // 共通のテストデータ生成メソッド
    private Employee createEmployee(Long id, String code, String name, String myNumber) {
        Employee e = new Employee();
        e.setId(id);
        e.setEmployeeCode(code);
        e.setName(name);
        e.setMyNumber(myNumber);
        return e;
    }

    // -------------------------------------------------------
    // ✅ 正常系テスト
    // -------------------------------------------------------

    @Test // 全件取得(findAll)で登録済みデータを正しく返すかを確認
    public void getAllEmployees() {
        // Given
        List<Employee> mockList = List.of(
                createEmployee(1L, "E001", "佐藤", "123456789012"),
                createEmployee(2L, "E002", "鈴木", "987654321098")
        );
        when(repository.findAll()).thenReturn(mockList);

        // When
        List<Employee> result = service.getAllEmployees();

        // Then
        assertEquals(2, result.size());
        assertEquals("佐藤", result.get(0).getName());
    }

    @Test // ID指定検索(findById)で該当社員が見つかる場合の動作を確認
    public void findById_found() {
        // Given
        Employee emp = createEmployee(1L, "E001", "田中", "111111111111");
        when(repository.findById(1L)).thenReturn(Optional.of(emp));

        // When
        Employee result = service.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals("田中", result.getName());
    }

    @Test // 新規登録(register)が正常に成功するケースを確認
    public void registerSuccess() {
        // Given
        Employee emp = createEmployee(null, "E100", "山田太郎", "123456789012");
        when(repository.existsByEmployeeCode("E100")).thenReturn(false);
        when(repository.existsByMyNumber("123456789012")).thenReturn(false);

        // When
        boolean result = service.register(emp);

        // Then
        assertTrue(result);
        assertNotNull(emp.getRegisteredAt());
        verify(repository).save(emp);
    }

    @Test // 更新(updateEmployee)が正常に成功するケースを確認
    public void updateSuccess() {
        // Given
        Employee existing = createEmployee(1L, "E001", "旧名", "000000000000");
        Employee updated = createEmployee(1L, "E002", "新名", "123456789012");
        updated.setRemarks("備考更新");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        // When
        String status = service.updateEmployee(updated);

        // Then
        assertEquals("OK", status);
        assertEquals("新名", existing.getName());
        assertEquals("E002", existing.getEmployeeCode());
        verify(repository).save(existing);
    }

    @Test // 削除(deleteById)がRepositoryに正しく委譲されるかを確認
    public void deleteById_callsDelete() {
        // Given
        Long id = 1L;

        // When
        service.deleteById(id);

        // Then
        verify(repository).deleteById(id);
    }

// -------------------------------------------------------
// ⚠ 異常系テスト
// -------------------------------------------------------

    @Test // 更新対象が存在しない場合に"NOT_FOUND"を返すことを確認
    public void updateNotFound() {
        // Given
        Employee updated = createEmployee(1L, "E001", "新名", "123456789012");
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // When
        String status = service.updateEmployee(updated);

        // Then
        assertEquals("NOT_FOUND", status);
        verify(repository, never()).save(any());
    }

    @Test // 社員コードが重複している場合に登録が拒否されることを確認
    public void registerDuplicateCode() {
        // Given
        Employee emp = createEmployee(null, "E001", "山田", "111111111111");
        when(repository.existsByEmployeeCode("E001")).thenReturn(true);

        // When
        boolean result = service.register(emp);

        // Then
        assertFalse(result);
        verify(repository, never()).save(any());
    }

    @Test // マイナンバーが重複している場合に登録が拒否されることを確認
    public void registerDuplicateMyNumber() {
        // Given
        Employee emp = createEmployee(null, "E001", "山田", "111111111111");
        when(repository.existsByMyNumber("111111111111")).thenReturn(true);

        // When
        boolean result = service.register(emp);

         // Then
        assertFalse(result);
        verify(repository, never()).save(any());
    }
}
