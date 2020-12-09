import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Division } from '../model/division';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { PatientAdmission } from '../../patients/model/patient-admission';
import { PatientAdmissionRequest } from '../../patients/model/patient-admission-request';

@Injectable({
  providedIn: 'root'
})
export class DivisionService {
  private readonly url = 'http://localhost:8080/api/divisions';

  constructor(private http: HttpClient) { }

  getDivisions(): Observable<Division[]> {
    return this.http.get(this.url).pipe(
      map((response: any) => response?._embedded?.divisions || [])
    );
  }

  getDivision(id: number): Observable<Division> {
    return this.http.get<Division>(`${this.url}/${id}`);
  }

  getAdmissions(id: number): Observable<PatientAdmission[]> {
    return this.http.get(`${this.url}/${id}/admissions`).pipe(
      map((response: any) => response?._embedded?.patientAdmissions || [])
    );
  }

  getAdmissionRequests(id: number): Observable<PatientAdmissionRequest[]> {
    return this.http.get(`${this.url}/${id}/admission-requests`).pipe(
      map((response: any) => response?._embedded?.patientAdmissionRequests || [])
    );
  }
}
