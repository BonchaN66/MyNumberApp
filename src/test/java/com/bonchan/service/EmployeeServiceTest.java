package com.bonchan.service;

import com.bonchan.model.Employee;
import com.bonchan.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Mockitoの拡張機能を使ってテストを実行するための設定
public class EmployeeServiceTest {

    @Mock // EmployeeRepositoryのモック（テスト用のダミー）を作成
    private EmployeeRepository repository;

    @InjectMocks // EmployeeServiceのインスタンスを作成し、@Mockのモックを注入する
    private EmployeeService service;

    // ----------------------------------------------
    // 正常系テスト（期待通りに動く場合のテスト）
    // ----------------------------------------------

    @Test // [一覧取得] findAll()で社員リストが返ることをテスト
    public void testGetAllEmployees() {
        // テスト用の社員オブジェクトを作成し、リストにまとめる
        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setName("佐藤");
        emp1.setMyNumber("123456789012");

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setName("鈴木");
        emp2.setMyNumber("987654321098");

        List<Employee> mockList = List.of(emp1, emp2);

        // repository.findAll()が呼ばれたらmockListを返すように設定
        when(repository.findAll()).thenReturn(mockList);

        // サービスのgetAllEmployees()を呼び出し、結果を受け取る
        List<Employee> result = service.getAllEmployees();

        // 結果の検証：社員リストのサイズが2であることをチェック
        assertEquals(2, result.size());
        // 先頭の社員の名前が「佐藤」であることをチェック
        assertEquals("佐藤", result.get(0).getName());
    }

    @Test // [個別取得] findById()で該当の社員が取得できることをテスト
    public void testFindById_found() {
        // 期待する社員オブジェクトを作成
        Employee emp = new Employee();
        emp.setId(1L);
        emp.setName("田中");
        emp.setMyNumber("111111111111");

        // repository.findById(1L)が呼ばれたらempを返すように設定
        when(repository.findById(1L)).thenReturn(Optional.of(emp));

        // サービスのfindById()を呼び出す
        Employee result = service.findById(1L);

        // 結果がnullでないことをテスト（社員が見つかった）
        assertNotNull(result);
        // 社員名が「田中」であることをテスト
        assertEquals("田中", result.getName());
    }

    @Test // [登録処理] register()でregisteredAtがセットされ、save()が呼ばれることをテスト
    public void testRegister_callsSave() {
        // 空の社員オブジェクトを作成
        Employee emp = new Employee();

        // 登録処理を呼び出す（登録日時をセットして保存する）
        service.register(emp);

        // 登録日時がセットされていることをテスト
        assertNotNull(emp.getRegisteredAt());
        // repository.save()が1回だけ呼ばれたか検証
        verify(repository, times(1)).save(emp);
    }

    @Test // [削除処理] deleteById()でdeleteById()が1回呼ばれることをテスト
    public void testDeleteById_callsDelete() {
        Long id = 1L;

        // 削除処理を呼び出す
        service.deleteById(id);

        // repository.deleteById()が1回だけ呼ばれたか検証
        verify(repository, times(1)).deleteById(id);
    }

    @Test // [更新成功] updateEmployee()で既存社員の情報が更新されることをテスト
    public void testUpdateEmployee_success() {
        // 既存社員オブジェクト（更新前）
        Employee existing = new Employee();
        existing.setId(1L);
        existing.setName("旧名");
        existing.setMyNumber("000000000000");
        existing.setEmployeeCode("E001");

        // 更新用社員オブジェクト（更新後の情報）
        Employee updated = new Employee();
        updated.setId(1L);
        updated.setName("新名");
        updated.setMyNumber("123456789012");
        updated.setEmployeeCode("E002");
        updated.setRemarks("備考更新");

        // repository.findById()は既存社員を返すように設定
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        // repository.save()は更新後の社員を返すように設定（モックの動作）
        when(repository.save(any(Employee.class))).thenReturn(updated);

        // updateEmployee()を呼び出す（既存社員が更新される）
        service.updateEmployee(updated);

        // フィールドが更新されているかチェック
        assertEquals("新名", existing.getName());
        assertEquals("123456789012", existing.getMyNumber());
        assertEquals("E002", existing.getEmployeeCode());
        assertEquals("備考更新", existing.getRemarks());

        // findById()が1回呼ばれたことを検証
        verify(repository, times(1)).findById(1L);
        // save()が1回呼ばれたことを検証
        verify(repository, times(1)).save(existing);
    }

    // ----------------------------------------------
    // 異常系テスト（エラーや例外が発生するケースのテスト）
    // ----------------------------------------------

    @Test // [個別取得失敗] findById()で存在しないIDを検索するとnullが返ることをテスト
    public void testFindById_notFound() {
        // 存在しないIDの時は空のOptionalを返すように設定
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // findById()の結果はnullになるはず
        Employee result = service.findById(1L);

        // nullであることをテスト
        assertNull(result);
    }

    @Test // [更新失敗] updateEmployee()で存在しないIDなら例外がスローされ、saveされないことをテスト
    public void testUpdateEmployee_notFound_throws() {
        // 更新用社員オブジェクトを作成
        Employee updated = new Employee();
        updated.setId(1L);
        updated.setName("新名");
        updated.setMyNumber("123456789012");
        updated.setEmployeeCode("E002");
        updated.setRemarks("備考更新");

        // 存在しないIDの場合は空のOptionalを返すように設定
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // updateEmployee()を呼んだらRuntimeExceptionが発生することをテスト
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.updateEmployee(updated);
        });

        // 例外メッセージをテスト
        assertEquals("該当従業員が存在しません", exception.getMessage());

        // findById()が1回呼ばれたことを検証
        verify(repository, times(1)).findById(1L);
        // save()は呼ばれていないことを検証
        verify(repository, never()).save(any());
    }
}
