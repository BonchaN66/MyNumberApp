package com.example.MyNumber.controller;

import com.example.MyNumber.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {

    private final List<Employee> employeeList = new ArrayList<>();

    // 一覧表示
    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("employeeList", employeeList);
        return "list";
    }

    // 登録フォーム表示
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "form";
    }

    // 登録処理
    @PostMapping("/register")
    public String register(@ModelAttribute Employee employee) {
        employee.setRegisteredAt(LocalDate.now());
        employeeList.add(employee);
        return "redirect:/list";
    }

    // 削除処理（社員IDで削除）
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        employeeList.removeIf(emp -> emp.getId().equals(id));
        return "redirect:/list";
    }
}
