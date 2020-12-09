import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { EmployeeService } from './employee.service';
import { Employee } from './model/employee';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css']
})
export class EmployeesComponent implements OnInit, OnDestroy {
  public loading = true;
  public employees: Employee[] = [];
  public error: any = null;

  private subscriptions: Subscription[] = [];

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.subscriptions.push(this.employeeService.getEmployees().subscribe(employees => {
      this.employees = employees;
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
