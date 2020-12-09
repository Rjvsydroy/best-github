import { Component, OnDestroy, OnInit } from '@angular/core';
import { PatientService } from './service/patient.service';
import { Subscription } from 'rxjs';
import { Patient } from './model/patient';

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit, OnDestroy {
  public loading = true;
  public patients: Patient[] = [];
  public error: any = null;

  private subscriptions: Subscription[] = [];

  constructor(private patientService: PatientService) { }

  ngOnInit(): void {
    this.subscriptions.push(this.patientService.getPatients().subscribe(patients => {
      this.patients = patients;
    }, error => {
      this.error = error;
    }, () => {
      this.loading = false;
    }));
  }

  delete(patientId: number): void {
    this.subscriptions.push(this.patientService.deletePatient(patientId).subscribe(() => {
      this.patients = this.patients.filter(p => p.id !== patientId);
    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

}
