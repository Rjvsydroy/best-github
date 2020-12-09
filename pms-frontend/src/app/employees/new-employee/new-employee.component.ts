import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { FormBuilder, FormControl, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Division } from '../../divisions/model/division';
import { DivisionService } from '../../divisions/service/division.service';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-employee',
  templateUrl: './new-employee.component.html',
  styleUrls: ['./new-employee.component.css']
})
export class NewEmployeeComponent implements OnInit, OnDestroy {
  public form = this.formBuilder.group({
    emailAddress: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    employeeNumber: ['', [Validators.required]],
    divisionId: [null, [this.requiredWhen('Doctor')]],
    phoneExtension: [null, [this.requiredWhen('ChiefNurse')]],
    bipperExtension: [null, [this.requiredWhen('ChiefNurse')]],
  });

  public employeeType = 'Employee';
  public divisions: Division[] = [];

  private subscriptions: Subscription[] = [];

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private employeeService: EmployeeService,
              private divisionService: DivisionService) { }

  ngOnInit(): void {
    this.subscriptions.push(this.divisionService.getDivisions().subscribe(divisions => {
      this.divisions = divisions;
    }));
  }

  onEmployeeTypeChanged(type: string): void {
    this.employeeType = type;

    this.form.get('divisionId').updateValueAndValidity();
    this.form.get('phoneExtension').updateValueAndValidity();
    this.form.get('bipperExtension').updateValueAndValidity();
  }

  submit(): void {
    let result;

    if (this.employeeType === 'ChiefNurse') {
      result = this.employeeService.createChiefNurse(this.form.value);
    } else if (this.employeeType === 'Doctor') {
      result = this.employeeService.createDoctor(this.form.value);
    } else {
      result = this.employeeService.createEmployee(this.form.value);
    }

    this.subscriptions.push(result.subscribe(() => {
      this.router.navigate(['employees']);
    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

  private requiredWhen(type: string): ValidatorFn {
    return (control: FormControl): ValidationErrors => {
      if (this.employeeType === type) {
        return Validators.required(control);
      } else {
        return {};
      }
    };
  }
}
