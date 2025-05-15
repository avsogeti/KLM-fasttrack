package com.airfranceklm.fasttrack.assignment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airfranceklm.fasttrack.assignment.resources.Employee;
import com.airfranceklm.fasttrack.assignment.service.EmployeeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeApi
{

   private final EmployeeService employeeService;

   @GetMapping
   public ResponseEntity<List<Employee>> getEmployees()
   {
      return new ResponseEntity<>(this.employeeService.getEmployee(), HttpStatus.OK);
   }

   @PostMapping
   public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee)
   {
      return new ResponseEntity<>(this.employeeService.createEmployee(employee), HttpStatus.CREATED);
   }
}
