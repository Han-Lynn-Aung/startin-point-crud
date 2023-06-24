package com.example.startinpointemployeecrud.controller;

import com.example.startinpointemployeecrud.model.Employee;
import com.example.startinpointemployeecrud.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping("/list")
    private String getAllEmployees(Model model) {
        List<Employee> employees = employeeRepo.findAll();
        model.addAttribute("employees", employees);
        return "employee-list";
    }

    @GetMapping("/create")
    private String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    @PostMapping("/create")
    private String createEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employee-form";
        }
        employeeRepo.save(employee);
        return "redirect:/api/employee/list";
    }

    @GetMapping("/edit/{id}")
    private String showEditForm(@PathVariable("id") long id, Model model) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee id: " + id));

        model.addAttribute("employee", employee);
        return "employee-form";
    }

    @PostMapping("/edit/{id}")
    private String updateEmployee(@PathVariable("id") long id,@Valid @ModelAttribute("employee") Employee employee,
                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "employee-form";
        }

        employee.setId(id);
        employeeRepo.save(employee);
        return "redirect:/api/employees/list";
    }

    @GetMapping("/delete/{id}")
    private String deleteEmployee(@PathVariable("id") long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee id: " + id));
        employeeRepo.delete(employee);
        return "redirect:/api/employees/list";
    }



    //Rest api
        /*    @GetMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }*/

   /* @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return employeeService.updateEmployee(id, employeeDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }*/


}
