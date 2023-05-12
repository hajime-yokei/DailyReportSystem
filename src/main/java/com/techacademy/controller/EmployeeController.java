package com.techacademy.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("employeelist", service.getEmployeeList());
        return "employee/list";
    }

    @GetMapping("/register")
    public String getRegister(@ModelAttribute Employee employee) {
        return "employee/register";
    }

    @PostMapping("/register")
    public String postRegister(Employee employee) {
        employee.setDelete_flag(0);
        employee.setCreated_at(LocalDateTime.now());
        employee.setUpdated_at(LocalDateTime.now());
        employee.getAuthentication().setEmployee(employee);
        service.saveEmployee(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/detail/{id}")
    public String getEmployee(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("employee", service.getEmployee(id));
        return "employee/detail";
    }

    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("employee", service.getEmployee(id));
        return "employee/update";
    }

    @PostMapping("/update/{id}")
    public String postEmployee(Employee employee) {
        service.saveEmployee(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id, Employee employee) {
        Employee employeetable = service.getEmployee(id);
        employeetable.setDelete_flag(1);
        service.saveEmployee(employeetable);
        return "redirect:/employee/list";
    }
}
