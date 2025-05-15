package com.airfranceklm.fasttrack.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airfranceklm.fasttrack.assignment.jpa.HolidayEntity;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayEntity, String>
{
   List<HolidayEntity> findHolidaysByEmployeeId(String employeeId);
}
