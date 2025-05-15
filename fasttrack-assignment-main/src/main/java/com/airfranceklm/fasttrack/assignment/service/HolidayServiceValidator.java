package com.airfranceklm.fasttrack.assignment.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.airfranceklm.fasttrack.assignment.jpa.HolidayEntity;
import com.airfranceklm.fasttrack.assignment.repository.HolidayRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HolidayServiceValidator
{
   private final HolidayRepository holidayRepository;

   public void validateHoliday(HolidayEntity holiday)
   {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate startOfHoliday = LocalDate.parse(holiday.getStartOfHoliday(), formatter);
      LocalDate endOfHoliday = LocalDate.parse(holiday.getEndOfHoliday(), formatter);
      List<HolidayEntity> existingHolidays = holidayRepository.findHolidaysByEmployeeId(holiday.getEmployeeId());

      // Check if the holiday overlaps with existing holidays
      for (HolidayEntity existingHoliday : existingHolidays)
      {
         LocalDate existingStart = LocalDate.parse(existingHoliday.getStartOfHoliday(), formatter);
         LocalDate existingEnd = LocalDate.parse(existingHoliday.getEndOfHoliday(), formatter);

         if (startOfHoliday.isBefore(existingEnd) && endOfHoliday.isAfter(existingStart))
         {
            throw new IllegalArgumentException("Holiday overlaps with an existing holiday");
         }
      }
   }
}
