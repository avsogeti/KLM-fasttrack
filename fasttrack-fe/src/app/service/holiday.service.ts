import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Holiday} from '../components/schedule-overview/interface/holiday';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class HolidayService {

  constructor(private http: HttpClient) {
  }

  public getHolidays(): Observable<Holiday[]> {
    return this.http.get<Holiday[]>('http://localhost:8080/holidays');
  }

  public getHolidaysByEmployeeID(employeeId: string): Observable<Holiday[]> {
    return this.http.get<Holiday[]>(`http://localhost:8080/holidays/${employeeId}`);
  }

  public createHoliday(holiday: Holiday): Observable<Holiday> {
    return this.http.post<Holiday>('http://localhost:8080/holidays', holiday)
  }

  public deleteHoliday(id: String) {
    return this.http.delete(`http://localhost:8080/holidays/${id}`);
  }
}
