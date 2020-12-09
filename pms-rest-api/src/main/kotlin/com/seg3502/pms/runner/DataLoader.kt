package com.seg3502.pms.runner

import com.seg3502.pms.entities.*
import com.seg3502.pms.repository.*
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime

@Component
class DataLoader(private val employeeRepository: EmployeeRepository,
                 private val divisionRepository: DivisionRepository,
                 private val patientRepository: PatientRepository,
                 private val closeRelativeRepository: CloseRelativeRepository,
                 private val patientAdmissionRepository: PatientAdmissionRepository,
                 private val patientAdmissionRequestRepository: PatientAdmissionRequestRepository,
                 private val prescriptionRepository: PrescriptionRepository,
                 private val prescriptionScheduleRepository: PrescriptionScheduleRepository,
                private val passwordEncoder: PasswordEncoder) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        employeeRepository.save(Employee("3146474529", "ebyrne@example.com", passwordEncoder.encode("password0"), "Edison", "Byrne"))

        val chiefNurse1 = employeeRepository.save(ChiefNurse("8925145685", "kroach@example.com", passwordEncoder.encode("password1"), "Kymani", "Roach", 1025, 3892))
        val chiefNurse2 = employeeRepository.save(ChiefNurse("4527894898", "kjackson@example.com", passwordEncoder.encode("password2"), "Khaleesi", "Jackson", 3145, 5838))

        divisionRepository.save(Division("ER-91", "Emergency", "South West Block", 20, 3825, chiefNurse1))
        val division2 = divisionRepository.save(Division("CA-33", "Cardiology", "North West Block", 30, 5387, chiefNurse2))
        val division3 = divisionRepository.save(Division("NE-68", "Neurology", "East Block", 10, 9005, chiefNurse2))

        val doctor1 = employeeRepository.save(Doctor("3568914298", "lfranco@example.com", passwordEncoder.encode("password3"), "Lylah", "Franco", division2))
        val doctor2 = employeeRepository.save(Doctor("6578537856", "gbrackman@example.com", passwordEncoder.encode("password4"), "Gene", "Brackman", division3))

        val patient1 = patientRepository.save(Patient("2389658", "Haley", "Hewitt", "4468 Jade St, North Vancouver, British Columbia", "604-960-7578", LocalDate.of(1999, 8, 20), "Ray G Williams", Sex.FEMALE, MaritalStatus.SINGLE))
        val patient2 = patientRepository.save(Patient("4761924", "Pedro", "Brackman", "1903 Bay Street, Toronto, Ontario", "647-219-2763", LocalDate.of(1991, 2, 3), "Rodolfo R Armijo", Sex.MALE, MaritalStatus.MARRIED))

        closeRelativeRepository.save(CloseRelative("Earle", "Marten", "4590 St. John Street, Annaheim, Saskatchewan", "Brother", "306-598-9229", patient1))
        closeRelativeRepository.save(CloseRelative("Terri", "Scott", "3981 Queens Sq, Hespeler, Ontario", "Mother", "519-651-0969", patient2))
        closeRelativeRepository.save(CloseRelative("Raymond", "Leger", "3784 Blind Bay Road, Pritchard, British Columbia", "Cousin", "250-577-9995", patient2))

        patientAdmissionRequestRepository.save(PatientAdmissionRequest("Reason 1", 9, patient1, doctor2))
        patientAdmissionRequestRepository.save(PatientAdmissionRequest("Reason 2", 5, patient2, doctor1))

        patientAdmissionRepository.save(PatientAdmission(patient2, doctor1, "L-286", "A13"))
        patientAdmissionRepository.save(PatientAdmission(patient1, doctor2, "G-323", "Y34", "35868193489"))

        val prescription1 = prescriptionRepository.save(Prescription(patient2, "ST543", "Acetaminophen 500mg"))

        prescriptionScheduleRepository.save(PrescriptionSchedule(LocalTime.of(12, 0, 0), 1, prescription1))
    }
}