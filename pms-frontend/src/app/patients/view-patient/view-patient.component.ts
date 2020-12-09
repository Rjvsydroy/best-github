import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PatientService } from '../service/patient.service';
import { Subscription } from 'rxjs';
import { Patient } from '../model/patient';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-view-patient',
  templateUrl: './view-patient.component.html',
  styleUrls: ['./view-patient.component.css']
})
export class ViewPatientComponent implements OnInit, OnDestroy {
  public loading = true;
  public patient: Patient;
  public error: any;

  public form = this.formBuilder.group({
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    insuranceNumber: ['', [Validators.required]],
    dateOfBirth: ['', [Validators.required]],
    address: ['', [Validators.required]],
    phoneNumber: ['', [Validators.required]],
    externalDoctor: ['', [Validators.required]],
    sex: ['', [Validators.required]],
    maritalStatus: ['', [Validators.required]]
  });

  private subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private patientService: PatientService,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.subscriptions.push(this.route.params.subscribe(params => {
      if (params.id) {
        this.subscriptions.push(this.patientService.getPatient(params.id).subscribe(patient => {
          this.patient = patient;
          this.form.patchValue(patient);
        }, error => {
          this.error = error;
        }, () => {
          this.loading = false;
        }));
      } else {
        this.patient = new Patient();
        this.loading = false;
      }
    }));
  }

  submit(): void {
    Object.assign(this.patient, this.form.value);

    if (this.patient.id) {
      this.subscriptions.push(this.patientService.updatePatient(this.patient).subscribe(() => {
        this.router.navigate(['patients']);
      }));
    } else {
      this.subscriptions.push(this.patientService.createPatient(this.patient).subscribe(() => {
        this.router.navigate(['patients']);
      }));
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

}
