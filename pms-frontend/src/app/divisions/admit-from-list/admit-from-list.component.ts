import { Component, OnDestroy, OnInit } from '@angular/core';
import { Doctor } from '../../employees/model/doctor';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { PatientService } from '../../patients/service/patient.service';
import { EmployeeService } from '../../employees/employee.service';

@Component({
  selector: 'app-admit-from-list',
  templateUrl: './admit-from-list.component.html',
  styleUrls: ['./admit-from-list.component.css']
})
export class AdmitFromListComponent implements  OnInit, OnDestroy {
  public loading = true;
  public error: any;
  public doctors: Doctor[] = [];

  public form = this.formBuilder.group({
    roomNumber: ['', [Validators.required]],
    bedNumber: ['', [Validators.required]],
    privateInsuranceNumber: ['']
  });

  private divisionId: number;
  private requestId: number;
  private subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private patientService: PatientService,
              private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.subscriptions.push(this.route.params.subscribe(params => {
      this.divisionId = params.id;
      this.requestId = params.requestId;
    }));

    this.subscriptions.push(this.employeeService.getDoctors().subscribe(doctors => {
      this.doctors = doctors;
    }));
  }

  submit(): void {
    this.patientService.admitPatientFromRequest(this.requestId, this.form.value).subscribe(() => {
      this.router.navigate(['divisions', this.divisionId]);
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }
}


