package com.seg3502.pms.assemblers

import com.seg3502.pms.controller.PatientsController
import com.seg3502.pms.entities.CloseRelative
import com.seg3502.pms.entities.Patient
import com.seg3502.pms.representation.CloseRelativeRepresentation
import com.seg3502.pms.representation.PatientRepresentation
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Component

@Component
class PatientAssembler : RepresentationModelAssemblerSupport<Patient, PatientRepresentation>(PatientsController::class.java, PatientRepresentation::class.java) {
    override fun toModel(entity: Patient): PatientRepresentation {
        val representation = instantiateModel(entity)

        representation.id = entity.id
        representation.insuranceNumber = entity.insuranceNumber
        representation.firstName = entity.firstName
        representation.lastName = entity.lastName
        representation.address = entity.address
        representation.phoneNumber = entity.phoneNumber
        representation.dateOfBirth = entity.dateOfBirth
        representation.externalDoctor = entity.externalDoctor
        representation.sex = entity.sex
        representation.maritalStatus = entity.maritalStatus
        representation.closeRelatives = closeRelativesRepresentation(entity.closeRelatives)

        representation.add(linkTo(methodOn(PatientsController::class.java).getPatientById(entity.id)).withSelfRel())

        return representation
    }

    override fun toCollectionModel(entities: MutableIterable<Patient>): CollectionModel<PatientRepresentation> {
        val representations = super.toCollectionModel(entities)

        representations.add(linkTo(methodOn(PatientsController::class.java).getPatients()).withSelfRel())

        return representations
    }

    private fun closeRelativesRepresentation(closeRelatives: List<CloseRelative>): List<CloseRelativeRepresentation> {
        return closeRelatives.map { cr -> closeRelativeRepresentation(cr) }
    }

    private fun closeRelativeRepresentation(closeRelative: CloseRelative): CloseRelativeRepresentation {
        val representation = CloseRelativeRepresentation()

        representation.id = closeRelative.id
        representation.firstName = closeRelative.firstName
        representation.lastName = closeRelative.lastName
        representation.address = closeRelative.address
        representation.relationship = closeRelative.relationship
        representation.phoneNumber = closeRelative.phoneNumber

        representation.add(linkTo(methodOn(PatientsController::class.java).getCloseRelativeById(closeRelative.patient.id, closeRelative.id)).withSelfRel())

        return representation
    }
}