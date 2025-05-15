package com.airfranceklm.fasttrack.assignment.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.airfranceklm.fasttrack.assignment.jpa.HolidayEntity;
import com.airfranceklm.fasttrack.assignment.repository.HolidayRepository;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HolidayService
{
   private final HolidayRepository holidayRepository;
   private final HolidayServiceValidator holidayServiceValidator;

   public List<Holiday> getHolidays()
   {
      return holidayRepository.findAll().stream().map(
                  entity -> new Holiday(entity.getHolidayId(), entity.getHolidayLabel(), entity.getEmployeeId(), entity.getStartOfHoliday(), entity.getEndOfHoliday(), entity.getStatus())
            )
            .toList();
   }

   public List<Holiday> getHolidaysByEmployeeId(String employeeId)
   {
      return holidayRepository.findHolidaysByEmployeeId(employeeId).stream().map(
                  entity -> new Holiday(entity.getHolidayId(), entity.getHolidayLabel(), entity.getEmployeeId(), entity.getStartOfHoliday(), entity.getEndOfHoliday(), entity.getStatus())
            )
            .toList();
   }

   public Holiday createHoliday(Holiday holiday)
   {
      HolidayEntity holidayEntity = new HolidayEntity();
      holidayEntity.setHolidayLabel(holiday.getHolidayLabel());
      holidayEntity.setEmployeeId(holiday.getEmployeeId());
      holidayEntity.setStartOfHoliday(holiday.getStartOfHoliday());
      holidayEntity.setEndOfHoliday(holiday.getEndOfHoliday());
      holidayEntity.setStatus(holiday.getStatus());

      //validator
      holidayServiceValidator.validateHoliday(holidayEntity);

      HolidayEntity savedEntity = holidayRepository.save(holidayEntity);
      return new Holiday(savedEntity.getHolidayId(), savedEntity.getHolidayLabel(), savedEntity.getEmployeeId(), savedEntity.getStartOfHoliday(), savedEntity.getEndOfHoliday(),
            savedEntity.getStatus());
   }

   public void deleteHolidayById(String holidayId)
   {
      holidayRepository.deleteById(holidayId);
   }
}