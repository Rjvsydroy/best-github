import { Patient } from './patient';
import { Doctor } from '../../employees/model/doctor';

export class PatientAdmissionRequest {
  id: number;
  requestReason: string;
  priority: number;
  patient: Patient;
  doctor: Doctor;
}
