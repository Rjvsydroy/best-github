package com.seg3502.pms.assemblers

import com.seg3502.pms.controller.DivisionsController
import com.seg3502.pms.entities.ChiefNurse
import com.seg3502.pms.entities.Division
import com.seg3502.pms.entities.Doctor
import com.seg3502.pms.representation.DivisionRepresentation
import com.seg3502.pms.representation.EmployeeRepresentation
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Component

@Component
class DivisionAssembler : RepresentationModelAssemblerSupport<Division, DivisionRepresentation>(DivisionsController::class.java, DivisionRepresentation::class.java) {
    override fun toModel(entity: Division): DivisionRepresentation {
        val representation = instantiateModel(entity)

        representation.id = entity.id
        representation.divisionNumber = entity.divisionNumber
        representation.divisionName = entity.divisionName
        representation.location = entity.location
        representation.numberOfBeds = entity.numberOfBeds
        representation.phoneExtension = entity.phoneExtension
        representation.availability = entity.availability
        representation.chiefNurse = chiefNurseRepresentation(entity.chiefNurse)
        representation.doctors = doctorRepresentations(entity.doctors)

        representation.add(linkTo(methodOn(DivisionsController::class.java).getDivisionById(entity.id)).withSelfRel())

        return representation
    }

    override fun toCollectionModel(entities: MutableIterable<Division>): CollectionModel<DivisionRepresentation> {
        val divisionRepresentations = super.toCollectionModel(entities)

        divisionRepresentations.add(linkTo(methodOn(DivisionsController::class.java).getDivisions()).withSelfRel())

        return divisionRepresentations
    }

    private fun chiefNurseRepresentation(chiefNurse: ChiefNurse): EmployeeRepresentation {
        val employeeRepresentation = EmployeeRepresentation()

        employeeRepresentation.id = chiefNurse.id
        employeeRepresentation.employeeNumber = chiefNurse.employeeNumber
        employeeRepresentation.emailAddress = chiefNurse.emailAddress
        employeeRepresentation.firstName = chiefNurse.firstName
        employeeRepresentation.lastName = chiefNurse.lastName
        employeeRepresentation.phoneExtension = chiefNurse.phoneExtension
        employeeRepresentation.bipperExtension = chiefNurse.bipperExtension

        return employeeRepresentation
    }

    private fun doctorRepresentations(doctors: List<Doctor>): List<EmployeeRepresentation> {
        return doctors.map { d -> doctorRepresentation(d) }
    }

    private fun doctorRepresentation(doctor: Doctor): EmployeeRepresentation {
        val employeeRepresentation = EmployeeRepresentation()

        employeeRepresentation.id = doctor.id
        employeeRepresentation.employeeNumber = doctor.employeeNumber
        employeeRepresentation.emailAddress = doctor.emailAddress
        employeeRepresentation.firstName = doctor.firstName
        employeeRepresentation.lastName = doctor.lastName

        return employeeRepresentation
    }
}