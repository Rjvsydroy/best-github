import { Component, OnDestroy, OnInit } from '@angular/core';
import { Patient } from '../../patients/model/patient';
import { FormArray, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PatientService } from '../../patients/service/patient.service';
import { PrescriptionService } from '../service/prescription.service';
import { forkJoin, Subscription } from 'rxjs';

@Component({
  selector: 'app-new-prescription',
  templateUrl: './new-prescription.component.html',
  styleUrls: ['./new-prescription.component.css']
})
export class NewPrescriptionComponent implements OnInit, OnDestroy {
  public loading = true;
  public patients: Patient[] = [];
  public error: any;

  public form = this.formBuilder.group({
    patientId: [null, [Validators.required]],
    medicationNumber: ['', [Validators.required]],
    medicationName: ['', [Validators.required]],
    schedule: this.formBuilder.array([], Validators.required)
  });

  get schedule(): FormArray {
    return this.form.get('schedule') as FormArray;
  }

  private subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private patientService: PatientService,
              private prescriptionService: PrescriptionService,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
      this.subscriptions.push(this.patientService.getPatients().subscribe(patients => {
        this.patients = patients;
      }, error => {
        this.error = error;
      }, () => {
        this.loading = false;
      }));

      this.addSchedule();
  }

  addSchedule(): void {
    this.schedule.push(this.formBuilder.group({
      unitsToAdminister: [1, [Validators.required]],
      timeOfDay: [null, [Validators.required]]
    }));
  }

  removeSchedule(i: number): void {
    this.schedule.removeAt(i);
  }

  submit(): void {
    this.prescriptionService.addPrescription(this.form.value).subscribe(prescription => {
      if (this.form.value.schedule.length === 0) {
        this.router.navigate(['prescriptions']);
        return;
      }

      const observables = [];

      this.form.value.schedule.forEach(schedule => {
        observables.push(this.prescriptionService.addPrescriptionSchedule(prescription.id, schedule));
      });

      forkJoin(observables).subscribe(() => {
        this.router.navigate(['prescriptions']);
      });
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }
}
