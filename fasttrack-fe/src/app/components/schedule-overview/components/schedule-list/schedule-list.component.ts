import {Component, model, ModelSignal} from '@angular/core';
import {Holiday} from '../../interface/holiday';
import {DatePipe} from '@angular/common';
import {HolidayService} from '../../../../service/holiday.service';
import {take} from 'rxjs';

@Component({
  selector: 'app-schedule-list',
  standalone: true,
  imports: [
    DatePipe
  ],
  templateUrl: './schedule-list.component.html',
  styleUrl: './schedule-list.component.scss'
})
export class ScheduleListComponent {

  public holidays:ModelSignal<Holiday[]> = model.required<Holiday[]>();

  constructor(private holidayService: HolidayService) {}

  deleteHoliday(holiday: Holiday):void {
    this.holidayService.deleteHoliday(holiday.holidayId).pipe(take(1)).subscribe(() => {
      this.holidays.update(() => this.holidays().filter(h => h.holidayId !== holiday.holidayId))
      }
    )
  }
}
