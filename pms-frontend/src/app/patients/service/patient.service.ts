import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Patient } from '../model/patient';
import { PatientAdmissionData } from '../model/patient-admission-data';
import { PatientAdmission } from '../model/patient-admission';
import { PatientAdmissionRequest } from '../model/patient-admission-request';
import { PatientAdmissionRequestData } from '../model/patient-admission-request-data';
import { AdmitPatientFromRequestData } from '../model/admit-patient-from-request-data';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private readonly url = 'http://localhost:8080/api/patients';

  constructor(private http: HttpClient) { }

  getPatients(): Observable<Patient[]> {
    return this.http.get(this.url).pipe(
      map((response: any) => response?._embedded?.patients || [])
    );
  }

  getPatient(id: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.url}/${id}`);
  }

  createPatient(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(this.url, patient);
  }

  updatePatient(patient: Patient): Observable<Patient> {
    return this.http.patch<Patient>(`${this.url}/${patient.id}`, patient);
  }

  deletePatient(id: number): Observable<any> {
    return this.http.delete(`${this.url}/${id}`);
  }

  requestPatientAdmission(id: number, data: PatientAdmissionRequestData): Observable<PatientAdmissionRequest> {
    return this.http.post<PatientAdmissionRequest>(`${this.url}/${id}/admission-requests`, data);
  }

  admitPatient(id: number, data: PatientAdmissionData): Observable<PatientAdmission> {
    return this.http.post<PatientAdmission>(`${this.url}/${id}/admit`, data);
  }

  admitPatientFromRequest(id: number, data: AdmitPatientFromRequestData): Observable<PatientAdmission> {
    return this.http.post<PatientAdmission>(`${this.url}/0/admission-requests/${id}/admit`, data);
  }

  deletePatientAdmission(patientId: number, admissionId: number): Observable<any> {
    return this.http.delete(`${this.url}/${patientId}/admissions/${admissionId}`);
  }
}
