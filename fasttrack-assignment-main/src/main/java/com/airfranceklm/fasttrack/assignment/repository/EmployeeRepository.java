package com.airfranceklm.fasttrack.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airfranceklm.fasttrack.assignment.jpa.EmployeeEntity;

   @Repository
   public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {}

