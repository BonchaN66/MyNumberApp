package com.bonchan.controller;

import com.bonchan.model.Employee;
import com.bonchan.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("employeeList", employeeService.getAllEmployees());
        return "list";
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        model.addAttribute("employee", new Employee());
        return "create";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute Employee employee,
            BindingResult result,
            Model model) {

        // Bean Validationのエラーを先にチェック
        if (result.hasErrors()) {
            return "create";
        }

        // 重複チェック
        boolean success = employeeService.register(employee);
        if (!success) {
            if (employeeService.existsByEmployeeCode(employee.getEmployeeCode())) {
                result.rejectValue("employeeCode", "duplicate", "従業員コードが既に存在します");
            }
            if (employee.getMyNumber() != null && !employee.getMyNumber().isEmpty()
                    && employeeService.existsByMyNumber(employee.getMyNumber())) {
                result.rejectValue("myNumber", "duplicate", "マイナンバーが既に存在します");
            }
            return "create"; // フォームに戻す
        }

        return "redirect:/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return "redirect:/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditCreate(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return "redirect:/list";
        }
        model.addAttribute("employee", employee);
        return "update";
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute Employee employee,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "update";
        }

        String status = employeeService.updateEmployee(employee);

        switch (status) {
            case "DUPLICATE_CODE":
                result.rejectValue("employeeCode", "duplicate", "従業員コードが既に存在します");
                return "update";
            case "DUPLICATE_MYNUMBER":
                result.rejectValue("myNumber", "duplicate", "マイナンバーが既に存在します");
                return "update";
            case "NOT_FOUND":
                result.reject("notfound", "対象の従業員が存在しません");
                return "update";
            case "OK":
            default:
                return "redirect:/list";
        }
    }
}
