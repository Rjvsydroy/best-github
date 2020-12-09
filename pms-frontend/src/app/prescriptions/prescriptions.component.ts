import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { PrescriptionService } from './service/prescription.service';
import { Prescription } from './model/prescription';

@Component({
  selector: 'app-prescriptions',
  templateUrl: './prescriptions.component.html',
  styleUrls: ['./prescriptions.component.css']
})
export class PrescriptionsComponent implements OnInit, OnDestroy {
  public loading = true;
  public prescriptions: Prescription[] = [];
  public error: any = null;

  private subscriptions: Subscription[] = [];

  constructor(private prescriptionService: PrescriptionService) { }

  ngOnInit(): void {
    this.subscriptions.push(this.prescriptionService.getPrescriptions().subscribe(prescriptions => {
      this.prescriptions = prescriptions;
    }, error => {
      this.error = error;
    }, () => {
      this.loading = false;
    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }
}
