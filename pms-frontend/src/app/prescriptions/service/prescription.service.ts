import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prescription } from '../model/prescription';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { PrescriptionSchedule } from '../model/prescription-schedule';

@Injectable({
  providedIn: 'root'
})
export class PrescriptionService {
  private readonly url = 'http://localhost:8080/api/prescriptions';

  constructor(private http: HttpClient) { }

  getPrescriptions(): Observable<Prescription[]> {
    return this.http.get(this.url).pipe(
      map((response: any) => response?._embedded?.prescriptions)
    );
  }

  addPrescription(prescription: Prescription): Observable<Prescription> {
    return this.http.post<Prescription>(this.url, prescription);
  }

  addPrescriptionSchedule(id: number, schedule: PrescriptionSchedule): Observable<PrescriptionSchedule> {
    return this.http.post<PrescriptionSchedule>(`${this.url}/${id}/schedule`, schedule);
  }

  getPrescription(id: number): Observable<Prescription> {
    return this.http.get<Prescription>(`${this.url}/${id}`);
  }
}
