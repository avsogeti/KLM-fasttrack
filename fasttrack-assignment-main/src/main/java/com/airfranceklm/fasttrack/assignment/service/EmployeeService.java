package com.airfranceklm.fasttrack.assignment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.airfranceklm.fasttrack.assignment.jpa.EmployeeEntity;
import com.airfranceklm.fasttrack.assignment.repository.EmployeeRepository;
import com.airfranceklm.fasttrack.assignment.resources.Employee;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService
{

   private final EmployeeRepository employeeRepository;

   public List<Employee> getEmployee()
   {

      return employeeRepository.findAll()
            .stream()
            .map(entity -> new Employee(entity.getEmployeeId(), entity.getName()))
            .toList();
   }

   public Employee createEmployee(Employee employee)
   {
      EmployeeEntity employeeEntity = new EmployeeEntity();
      employeeEntity.setName(employee.getName());

      EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);
      return new Employee(savedEntity.getEmployeeId(), savedEntity.getName());
   }
}
