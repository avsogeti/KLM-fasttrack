package com.airfranceklm.fasttrack.assignment.resources;

import com.airfranceklm.fasttrack.assignment.resources.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {
    private String holidayId;
    private String holidayLabel;
    private String employeeId;
    private String startOfHoliday;
    private String endOfHoliday;
    private Status status;
}
