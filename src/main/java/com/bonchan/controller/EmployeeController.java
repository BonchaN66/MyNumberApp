package com.bonchan.controller;

import com.bonchan.model.Employee;
import com.bonchan.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

// Webリクエストの入口、画面表示やフォーム処理を担当
@Controller
public class EmployeeController {

    // コンストラクタインジェクション（Spring推奨）
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 社員一覧表示（GET /list）
    @GetMapping("/list")
    public String showList(Model model) {
        // Serviceから社員リストを取得しModelにセット
        model.addAttribute("employeeList", employeeService.getAllEmployees());
        // list.html に遷移（Thymeleafで画面表示）
        return "list";
    }

    // 登録フォーム表示（GET /create）
    @GetMapping("/create")
    public String showCreate(Model model) {
        // 空のEmployeeオブジェクトをModelにセット（フォームバインド用）
        model.addAttribute("employee", new Employee());
        // create.html に遷移
        return "create";
    }

    // 登録処理（POST /register）
    @PostMapping("/register")
    public String register(
            // フォームから送られてくるEmployeeを検証
            @Valid @ModelAttribute Employee employee,
            // バリデーション結果を受け取る
            BindingResult result,
            Model model) {

        // バリデーションエラーがある場合、再度フォームに戻す
        if (result.hasErrors()) {
            model.addAttribute("employee", employee); // 入力済みデータを戻す
            return "create"; // エラーがある場合はcreate.htmlを再表示
        }

        // バリデーションを通過したらServiceに登録処理を委譲
        employeeService.register(employee);

        // 処理後は一覧画面にリダイレクト
        return "redirect:/list";
    }

    // 削除処理（POST /delete/{id}）
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        // Serviceに削除処理を委譲
        employeeService.deleteById(id);
        // 処理後は一覧画面にリダイレクト
        return "redirect:/list";
    }

    // 編集フォーム表示（GET /edit/{id}）
    @GetMapping("/edit/{id}")
    public String showEditCreate(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return "redirect:/list";
        }
        model.addAttribute("employee", employee);
        return "update";
    }

    // 更新処理（POST /update）
    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute Employee employee,
            BindingResult result,
            Model model) {

        // バリデーションエラーがある場合、再度フォームに戻す
        if (result.hasErrors()) {
            model.addAttribute("employee", employee); // 入力済みデータを戻す
            return "update"; // update.html を再表示
        }

        // バリデーションを通過したらServiceに更新処理を委譲
        employeeService.updateEmployee(employee);

        // 処理後は一覧画面にリダイレクト
        return "redirect:/list";
    }
}
