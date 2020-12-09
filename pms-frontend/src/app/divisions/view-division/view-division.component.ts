import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Division } from '../model/division';
import { DivisionService } from '../service/division.service';
import { PatientAdmissionRequest } from '../../patients/model/patient-admission-request';
import { PatientAdmission } from '../../patients/model/patient-admission';
import { PatientService } from '../../patients/service/patient.service';

@Component({
  selector: 'app-view-division',
  templateUrl: './view-division.component.html',
  styleUrls: ['./view-division.component.css']
})
export class ViewDivisionComponent implements OnInit, OnDestroy {
  public loading = [true, true, true];
  public division: Division;
  public admissions: PatientAdmission[] = [];
  public admissionRequests: PatientAdmissionRequest[] = [];
  public error: any[] = [null, null, null];

  private subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute,
              private patientService: PatientService,
              private divisionService: DivisionService) { }

  ngOnInit(): void {
    this.subscriptions.push(this.route.params.subscribe(params => {
      this.subscriptions.push(this.divisionService.getDivision(params.id).subscribe(division => {
        this.division = division;
      }, error => {
        this.error[0] = error;
      }, () => {
        this.loading[0] = false;
      }));

      this.subscriptions.push(this.divisionService.getAdmissions(params.id).subscribe(admissions => {
        this.admissions = admissions;
      }, error => {
        this.error[1] = error;
      }, () => {
        this.loading[1] = false;
      }));

      this.subscriptions.push(this.divisionService.getAdmissionRequests(params.id).subscribe(admissionRequests => {
        this.admissionRequests = admissionRequests;
      }, error => {
        this.error[2] = error;
      }, () => {
        this.loading[2] = false;
      }));
    }));
  }

  discharge(patientId: number, admissionId: number): void {
    this.subscriptions.push(this.patientService.deletePatientAdmission(patientId, admissionId).subscribe(() => {
      this.admissions = this.admissions.filter(a => a.id !== admissionId);
    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

}
