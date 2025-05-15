package com.airfranceklm.fasttrack.assignment.jpa;

import com.airfranceklm.fasttrack.assignment.resources.enums.Status;

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
@Table(name = "holiday")
public class HolidayEntity
{
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @NotNull
   @Column(name = "holidayId", nullable = false, unique = true, updatable = false)
   private String holidayId;

   @NotNull
   @Column(name = "holidayLabel", nullable = false)
   private String holidayLabel;

   @NotNull
   @Column(name = "employeeId", nullable = false)
   private String employeeId;

   @Column(name = "startOfHoliday")
   private String startOfHoliday;

   @Column(name = "endOfHoliday")
   private String endOfHoliday;

   @NotNull
   @Column(name = "status", nullable = false)
   private Status status;
}
