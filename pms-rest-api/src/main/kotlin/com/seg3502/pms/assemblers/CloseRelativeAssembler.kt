package com.seg3502.pms.assemblers

import com.seg3502.pms.controller.PatientsController
import com.seg3502.pms.entities.CloseRelative
import com.seg3502.pms.entities.Patient
import com.seg3502.pms.representation.CloseRelativeRepresentation
import com.seg3502.pms.representation.PatientRepresentation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Component

@Component
class CloseRelativeAssembler : RepresentationModelAssemblerSupport<CloseRelative, CloseRelativeRepresentation>(PatientsController::class.java, CloseRelativeRepresentation::class.java) {
    override fun toModel(entity: CloseRelative): CloseRelativeRepresentation {
        val representation = instantiateModel(entity)

        representation.id = entity.id
        representation.firstName = entity.firstName
        representation.lastName = entity.lastName
        representation.address = entity.address
        representation.relationship = entity.relationship
        representation.phoneNumber = entity.phoneNumber
        representation.patient = patientRepresentation(entity.patient)

        representation.add(linkTo(methodOn(PatientsController::class.java).getCloseRelativeById(entity.patient.id, entity.id)).withSelfRel())

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
}