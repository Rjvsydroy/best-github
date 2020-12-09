import { Patient } from './patient';
import { Employee } from '../../employees/model/employee';

export class PatientAdmission {
  id: number;
  patient: Patient;
  doctor: Employee;
  roomNumber: string;
  bedNumber: string;
  privateInsuranceNumber?: string;
}
