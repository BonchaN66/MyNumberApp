package com.bonchan.controller;

import com.bonchan.model.Employee;
import com.bonchan.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    // 登録フォーム表示（GET /form）
    @GetMapping("/form")
    public String showForm(Model model) {
        // 空のEmployeeオブジェクトをModelにセット（フォームバインド用）
        model.addAttribute("employee", new Employee());
        // form.html に遷移
        return "form";
    }

    // 登録処理（POST /register）
    @PostMapping("/register")
    public String register(@ModelAttribute Employee employee) {
        // Serviceに登録処理を委譲
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
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return "redirect:/list";
        }
        model.addAttribute("employee", employee);
        return "update";
    }

    // 更新処理（POST /update）
    @PostMapping("/update")
    public String update(@ModelAttribute Employee employee) {
        employeeService.updateEmployee(employee);
        return "redirect:/list";
    }
}
