package com.airfranceklm.fasttrack.assignment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import com.airfranceklm.fasttrack.assignment.service.HolidayService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/holidays")
@AllArgsConstructor
public class HolidaysApi
{

   private final HolidayService holidayService;

   //get all holidays
   @GetMapping
   public ResponseEntity<List<Holiday>> getHolidays()
   {
      return new ResponseEntity<>(this.holidayService.getHolidays(), HttpStatus.OK);
   }

   //get all holidays of specific employee by employeeId
   @GetMapping(value = "/{id}")
   public ResponseEntity<List<Holiday>> getHolidaysForEmployeeByEmployeeId(@PathVariable("id") String employeeId)
   {
      return new ResponseEntity<>(this.holidayService.getHolidaysByEmployeeId(employeeId), HttpStatus.OK);
   }

   //post holiday
   @PostMapping
   public ResponseEntity<Holiday> createHoliday(@RequestBody Holiday holiday)
   {
      try
      {
         return new ResponseEntity<>(this.holidayService.createHoliday(holiday), HttpStatus.CREATED);
      }
      catch (IllegalArgumentException i)
      {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
               .header("Error-Message", i.getMessage())
               .build();
      }
      catch (Exception e)
      {
         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }

   //delete holiday
   @DeleteMapping(value = "/{id}")
   public ResponseEntity<Void> deleteHolidayById(@PathVariable("id") String holidayId)
   {
      try
      {
         this.holidayService.deleteHolidayById(holidayId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
}
