import { Patient } from '../../patients/model/patient';
import { PrescriptionSchedule } from './prescription-schedule';

export class Prescription {
  id: number;
  patient: Patient;
  medicationNumber: string;
  medicationName: string;
  unitsPerDay: number;
  administrationsPerDay: number;
  schedule: PrescriptionSchedule[];
}
