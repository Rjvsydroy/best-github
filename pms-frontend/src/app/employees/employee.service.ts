import { Injectable } from '@angular/core';
import { Employee } from './model/employee';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Doctor } from './model/doctor';
import { EmployeeData } from './model/employee-data';
import { DoctorData } from './model/doctor-data';
import { ChiefNurseData } from './model/chief-nurse-data';
import { ChiefNurse } from './model/chief-nurse';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private readonly url = 'http://localhost:8080/api/employees';

  constructor(private http: HttpClient) { }

  public getEmployees(): Observable<Employee[]> {
    return this.http.get(this.url).pipe(
      map((response: any) => response?._embedded?.employees || [])
    );
  }

  public getDoctors(): Observable<Doctor[]> {
    return this.http.get(`${this.url}/doctors`).pipe(
      map((response: any) => response?._embedded?.employees || [])
    );
  }

  public createEmployee(data: EmployeeData): Observable<Employee> {
    return this.http.post<Employee>(`${this.url}`, data);
  }

  public createDoctor(data: DoctorData): Observable<Doctor> {
    return this.http.post<Doctor>(`${this.url}/doctors`, data);
  }

  public createChiefNurse(data: ChiefNurseData): Observable<ChiefNurse> {
    return this.http.post<ChiefNurse>(`${this.url}/chief-nurses`, data);
  }
}
