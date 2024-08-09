package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService theEmployeeService){
        employeeService=theEmployeeService;
    }

    @GetMapping("/list")
    public String listEmployee(Model theModel){

        //get the employees from database and add them to model
        List<Employee> employees = employeeService.findAll();
        theModel.addAttribute("employees",employees);

        return "employees/list-employees";
    }

    @GetMapping("/showFormAdd")
    public String showFromAdd(Model theModel){

        Employee employee = new Employee();

        theModel.addAttribute("employee",employee);

        return "employees/employee-form";
    }

    @GetMapping("/showFormUpdate")
    public String showFormUpdate(@ModelAttribute("employeeId") int id,Model theModel){

        //get the employee form service
        Employee employee = employeeService.findById(id);

        //set the employee in the model to fill the form field
        theModel.addAttribute("employee",employee);

        // send over to our form
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){
        //save the employee
        employeeService.save(theEmployee);

        //prevent duplicate by using redirect
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute("employeeId") int id){

        employeeService.deleteById(id);

        return "redirect:/employees/list";
    }
}
