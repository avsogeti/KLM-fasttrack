package com.airfranceklm.fasttrack.assignment.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class EmployeeEntity
{
   @Id
   @NotNull
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "employeeId", nullable = false, updatable = false, unique = true)
   private String employeeId;

   @NotNull
   @Column(name = "name")
   private String name;
}


