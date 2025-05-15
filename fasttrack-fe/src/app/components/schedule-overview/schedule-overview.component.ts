import {Component} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {EmployeeService} from '../../service/employee.service';
import {HolidayService} from '../../service/holiday.service';
import {Status} from './enum/status';
import {Holiday} from './interface/holiday';
import {ScheduleListComponent} from './components/schedule-list/schedule-list.component';
import {take} from 'rxjs';

@Component({
  selector: 'app-schedule-overview',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ScheduleListComponent,
  ],
  templateUrl: './schedule-overview.component.html',
  styleUrl: './schedule-overview.component.scss'
})
export class ScheduleOverviewComponent {
  public employeeForm: FormGroup;
  public holidayForm: FormGroup;
  public employeeId: string;
  public statusList: string[] = [Status.DRAFT, Status.REQUESTED, Status.SCHEDULED, Status.ARCHIVED];
  public holidayList: Holiday[] = [];

  constructor(private formBuilder: FormBuilder, private employeeService: EmployeeService, private holidayService: HolidayService) {
    this.employeeForm = this.getEmployeeForm();
    this.holidayForm = this.getHolidayForm();
  }

  getEmployeeForm():FormGroup {
    return this.formBuilder.group({
      employeeId: [null, Validators.required],
      name: ['', Validators.required],
    });
  }

  getHolidayForm():FormGroup {
    return this.formBuilder.group({
      holidayLabel: ['', Validators.required],
      employeeId: ['', Validators.required],
      startOfHoliday: [''],
      endOfHoliday: [''],
      status: ['', Validators.required],
    })
  }

  getEmployeeName(): AbstractControl {
    return this.employeeForm.get('name');
  }

  public createEmployee():void {
    this.employeeService.createEmployee({name: this.getEmployeeName().value}).pipe(take(1)).subscribe(data => {
      this.employeeId = data.employeeId;
    })
  }

  public createHolidayOnClick():void{
      this.holidayService.getHolidaysByEmployeeID(this.employeeForm.get("employeeId").value).pipe(take(1)).subscribe(data => {
        const startOfHoliday = this.holidayForm.get('startOfHoliday').value;
        const endOfHoliday = this.holidayForm.get('endOfHoliday').value;
        const isOverlapping = data.some(holiday => {
          return (new Date(startOfHoliday) <= new Date(holiday.endOfHoliday)) && (new Date(endOfHoliday) >= new Date(holiday.startOfHoliday));
        });
        if(isOverlapping){
          alert('The holiday dates overlap with existing holidays');
        } else{
          this.createHoliday()
        }
      })
  }

  public createHoliday():void  {
    this.holidayService.createHoliday({
      holidayLabel: this.holidayForm.get('holidayLabel').value,
      employeeId: this.holidayForm.get('employeeId').value,
      startOfHoliday: this.holidayForm.get('startOfHoliday').value,
      endOfHoliday: this.holidayForm.get('endOfHoliday').value,
      status: this.holidayForm.get('status').value
    }).pipe(take(1)).subscribe(data => {
      this.holidayList = [...this.holidayList, data];
    })
  }

  public getHolidays():void {
    this.holidayList = [];
    this.holidayService.getHolidaysByEmployeeID(this.employeeForm.get('employeeId').value).pipe(take(1)).subscribe(data => {
      this.holidayList = [...this.holidayList, ...data];
    })
  }

  //normally i remove this, but i was busy to combine the validators in a combineLatest then map over it to show alert, (because alerts are quick.
  // i also want to reuse the fetches if possible so no duplicate fetches. But sadly run out of time..
  //and ofcourse remove the subscribe in subscribe..

  // public createHolidayOnClick(): void {
  //   const startOfHoliday = new Date(this.holidayForm.get('startOfHoliday').value);
  //   const endOfHoliday = new Date(this.holidayForm.get('endOfHoliday').value);
  //
  //   combineLatest([
  //     this.holidayIsOverlapping$(startOfHoliday, endOfHoliday),
  //     this.isHolidayWithinThreeDays$()
  //   ]).pipe(
  //     map(([isOverlapping, hasWorkingDaysGap]) => {
  //       if (isOverlapping) {
  //         alert('The holiday dates overlap with existing holidays');
  //       } else if (hasWorkingDaysGap) {
  //         alert('There is a gap of 3 working days before or after the holiday');
  //       } else {
  //         this.createHoliday();
  //       }
  //     })
  //   ).pipe(take(1)).subscribe();
  // }

  // private holidayIsOverlapping$ (startOfHoliday: Date, endOfHoliday: Date): Observable<boolean>{
  //   return this.holidayService.getHolidaysByEmployeeID(this.employeeForm.get("employeeId").value).pipe(
  //     map(data =>
  //       data.some(holiday => (startOfHoliday <= new Date(holiday.endOfHoliday)) && (endOfHoliday >= new Date(holiday.startOfHoliday))
  //       )
  //     )
  //   )
  // }

  // private isHolidayWithinThreeDays$(): Observable<boolean> {
  //   const startOfHoliday = new Date(this.holidayForm.get('startOfHoliday').value);
  //   const endOfHoliday = new Date(this.holidayForm.get('endOfHoliday').value);
  //
  //   return this.holidayService.getHolidaysByEmployeeID(this.employeeForm.get('employeeId').value).pipe(
  //     map(holidays => {
  //       return holidays.some(holiday => {
  //         const holidayStart = new Date(holiday.startOfHoliday);
  //         const holidayEnd = new Date(holiday.endOfHoliday);
  //
  //         const gapBefore = this.calculateWorkingDays(holidayEnd, startOfHoliday);
  //         const gapAfter = this.calculateWorkingDays(endOfHoliday, holidayStart);
  //
  //         return gapBefore < 3 || gapAfter < 3;
  //       });
  //     })
  //   );
  // }

  // private calculateWorkingDays(start: Date, end: Date): number {
  //   if (!(start instanceof Date) || !(end instanceof Date) || isNaN(start.getTime()) || isNaN(end.getTime())) {
  //     throw new Error('Invalid date input');
  //   }
  //
  //   if (start > end) {
  //     return 0; // Return 0 if the start date is after the end date
  //   }
  //
  //   let count = 0;
  //   let current = new Date(start.getTime()); // Create a copy of the start date
  //
  //   while (current <= end) { // Include the end date
  //     const day = current.getDay();
  //     if (day !== 0 && day !== 6) { // Exclude Sundays (0) and Saturdays (6)
  //       count++;
  //     }
  //     current.setDate(current.getDate() + 1); // Move to the next day
  //   }
  //
  //   return count;
  // }
}
