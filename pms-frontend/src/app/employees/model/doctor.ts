import { Employee } from './employee';
import { Division } from '../../divisions/model/division';

export class Doctor extends Employee {
  division: Division;
}
