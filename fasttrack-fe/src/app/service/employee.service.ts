import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Employee} from '../components/schedule-overview/interface/employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient) { }

  public getEmployees():Observable<Employee[]> {
    return this.http.get<Employee[]>('http://localhost:8080/employees');
  }

  public createEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>('http://localhost:8080/employees', employee)
  }
}
