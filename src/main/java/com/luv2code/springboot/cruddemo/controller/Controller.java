package com.luv2code.springboot.cruddemo.controller;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/employees")
public class Controller {
    private EmployeeService employeeService;
    @Autowired
    public Controller(EmployeeService employeeService){
        this.employeeService= employeeService; // constructor injection for injecting the values directly to the constructor.
    }
    @GetMapping("/list")
    public String listEmployees(Model themodel){
        // get the employees from the database
        List<Employee> findAllEmployees= employeeService.findAll();

        // add that to the Spring model
        themodel.addAttribute("employeelist", findAllEmployees);
        return "employees/employeeslist";
    }

    @GetMapping("/showFormForAdd")
    public String formForAdd(Model theModel){
        Employee theemployee= new Employee();
        theModel.addAttribute("employeeobject", theemployee);
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String processEmployeeAndSave(@ModelAttribute("employeeobject") Employee employee){
        System.out.println(employee);
        employeeService.save(employee);
        return "redirect:/employees/list"; // this is special syntax for redirecting back to the employee page.
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theID, Model theModel){
        Employee theEmployee= employeeService.findById(theID); // find the suitable employee with that particular id
        theModel.addAttribute("employee", theEmployee);
        return "employees/employee-update-form";
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("employeeId") int theId){
        System.out.println(theId);
        employeeService.deleteById(theId);
        return "redirect:/employees/list";
    }
}
