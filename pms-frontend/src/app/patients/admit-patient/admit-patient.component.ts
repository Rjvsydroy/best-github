import { Component, OnDestroy, OnInit } from '@angular/core';
import { Patient } from '../model/patient';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { PatientService } from '../service/patient.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../../employees/employee.service';
import { Doctor } from '../../employees/model/doctor';

@Component({
  selector: 'app-admit-patient',
  templateUrl: './admit-patient.component.html',
  styleUrls: ['./admit-patient.component.css']
})
export class AdmitPatientComponent implements OnInit, OnDestroy {
  public loading = true;
  public error: any;
  public patient: Patient;
  public doctors: Doctor[] = [];

  public form = this.formBuilder.group({
    doctorId: [null, [Validators.required]],
    roomNumber: ['', [Validators.required]],
    bedNumber: ['', [Validators.required]],
    privateInsuranceNumber: ['']
  });

  private subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private patientService: PatientService,
              private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.subscriptions.push(this.route.params.subscribe(params => {
      this.subscriptions.push(this.patientService.getPatient(params.id).subscribe(patient => {
        this.patient = patient;
      }));
    }));

    this.subscriptions.push(this.employeeService.getDoctors().subscribe(doctors => {
      this.doctors = doctors;
    }));
  }

  submit(): void {
    this.patientService.admitPatient(this.patient.id, this.form.value).subscribe(() => {
      this.router.navigate(['patients']);
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }
}
