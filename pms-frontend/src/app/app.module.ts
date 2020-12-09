import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PatientsComponent } from './patients/patients.component';
import { HttpClientModule } from '@angular/common/http';
import { EditPatientComponent } from './patients/edit-patient/edit-patient.component';
import { ViewPatientComponent } from './patients/view-patient/view-patient.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdmitPatientComponent } from './patients/admit-patient/admit-patient.component';
import { RequestPatientAdmissionComponent } from './patients/request-patient-admission/request-patient-admission.component';
import { DivisionsComponent } from './divisions/divisions.component';
import { ViewDivisionComponent } from './divisions/view-division/view-division.component';
import { AdmitFromListComponent } from './divisions/admit-from-list/admit-from-list.component';
import { EmployeesComponent } from './employees/employees.component';
import { NewEmployeeComponent } from './employees/new-employee/new-employee.component';
import { SignInComponent } from './user/sign-in/sign-in.component';
import { authInterceptorProviders } from './authentication/authentication-interceptor.service';
import { PrescriptionsComponent } from './prescriptions/prescriptions.component';
import { NewPrescriptionComponent } from './prescriptions/new-prescription/new-prescription.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PatientsComponent,
    EditPatientComponent,
    AdmitPatientComponent,
    ViewPatientComponent,
    RequestPatientAdmissionComponent,
    DivisionsComponent,
    ViewDivisionComponent,
    AdmitFromListComponent,
    EmployeesComponent,
    NewEmployeeComponent,
    SignInComponent,
    PrescriptionsComponent,
    NewPrescriptionComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    AppRoutingModule,
    RouterModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
