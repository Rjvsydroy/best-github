import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PatientsComponent } from './patients/patients.component';
import { EditPatientComponent } from './patients/edit-patient/edit-patient.component';
import { ViewPatientComponent } from './patients/view-patient/view-patient.component';
import { AdmitPatientComponent } from './patients/admit-patient/admit-patient.component';
import { RequestPatientAdmissionComponent } from './patients/request-patient-admission/request-patient-admission.component';
import { DivisionsComponent } from './divisions/divisions.component';
import { ViewDivisionComponent } from './divisions/view-division/view-division.component';
import { AdmitFromListComponent } from './divisions/admit-from-list/admit-from-list.component';
import { EmployeesComponent } from './employees/employees.component';
import { NewEmployeeComponent } from './employees/new-employee/new-employee.component';
import { SignInComponent } from './user/sign-in/sign-in.component';
import { LoggedInGuard } from './authentication/logged-in.guard';
import { PrescriptionsComponent } from './prescriptions/prescriptions.component';
import { NewPrescriptionComponent } from './prescriptions/new-prescription/new-prescription.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'patients', component: PatientsComponent, canActivate: [LoggedInGuard] },
  { path: 'patients/new', component: EditPatientComponent, canActivate: [LoggedInGuard] },
  { path: 'patients/:id', component: EditPatientComponent, canActivate: [LoggedInGuard] },
  { path: 'patients/:id/view', component: ViewPatientComponent, canActivate: [LoggedInGuard] },
  { path: 'patients/:id/admit', component: AdmitPatientComponent, canActivate: [LoggedInGuard] },
  { path: 'patients/:id/request-admission', component: RequestPatientAdmissionComponent, canActivate: [LoggedInGuard] },
  { path: 'prescriptions', component: PrescriptionsComponent, canActivate: [LoggedInGuard] },
  { path: 'prescriptions/new', component: NewPrescriptionComponent, canActivate: [LoggedInGuard] },
  { path: 'divisions', component: DivisionsComponent, canActivate: [LoggedInGuard] },
  { path: 'divisions/:id', component: ViewDivisionComponent, canActivate: [LoggedInGuard] },
  { path: 'divisions/:id/admit/:requestId', component: AdmitFromListComponent, canActivate: [LoggedInGuard] },
  { path: 'employees', component: EmployeesComponent, canActivate: [LoggedInGuard] },
  { path: 'employees/new', component: NewEmployeeComponent },
  { path: 'sign-in', component: SignInComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ]
})
export class AppRoutingModule { }
