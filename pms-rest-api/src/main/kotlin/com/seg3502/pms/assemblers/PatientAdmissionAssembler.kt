package com.seg3502.pms.assemblers

import com.seg3502.pms.controller.EmployeesController
import com.seg3502.pms.controller.PatientsController
import com.seg3502.pms.entities.Doctor
import com.seg3502.pms.entities.Patient
import com.seg3502.pms.entities.PatientAdmission
import com.seg3502.pms.representation.EmployeeRepresentation
import com.seg3502.pms.representation.PatientAdmissionRepresentation
import com.seg3502.pms.representation.PatientRepresentation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Component

@Component
class PatientAdmissionAssembler : RepresentationModelAssemblerSupport<PatientAdmission, PatientAdmissionRepresentation>(PatientsController::class.java, PatientAdmissionRepresentation::class.java) {
    override fun toModel(entity: PatientAdmission): PatientAdmissionRepresentation {
        val representation = PatientAdmissionRepresentation()
        
        representation.id = entity.id
        representation.patient = patientRepresentation(entity.patient)
        representation.doctor = doctorRepresentation(entity.doctor)
        representation.roomNumber = entity.roomNumber
        representation.bedNumber = entity.bedNumber
        representation.privateInsuranceNumber = entity.privateInsuranceNumber
        
        return representation
    }

    private fun patientRepresentation(patient: Patient): PatientRepresentation {
        val representation = PatientRepresentation()

        representation.id = patient.id
        representation.insuranceNumber = patient.insuranceNumber
        representation.firstName = patient.firstName
        representation.lastName = patient.lastName
        representation.address = patient.address
        representation.phoneNumber = patient.phoneNumber
        representation.dateOfBirth = patient.dateOfBirth
        representation.externalDoctor = patient.externalDoctor
        representation.sex = patient.sex
        representation.maritalStatus = patient.maritalStatus

        representation.add(linkTo(methodOn(PatientsController::class.java).getPatientById(patient.id)).withSelfRel())

        return representation
    }

    private fun doctorRepresentation(doctor: Doctor): EmployeeRepresentation {
        val representation = EmployeeRepresentation()

        representation.id = doctor.id
        representation.employeeNumber = doctor.employeeNumber
        representation.emailAddress = doctor.emailAddress
        representation.firstName = doctor.firstName
        representation.lastName = doctor.lastName

        representation.add(linkTo(methodOn(EmployeesController::class.java).getEmployeeById(doctor.id)).withSelfRel())

        return representation
    }
}