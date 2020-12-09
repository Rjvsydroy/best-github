package com.seg3502.pms.assemblers

import com.seg3502.pms.controller.EmployeesController
import com.seg3502.pms.entities.ChiefNurse
import com.seg3502.pms.entities.Division
import com.seg3502.pms.entities.Doctor
import com.seg3502.pms.entities.Employee
import com.seg3502.pms.representation.DivisionRepresentation
import com.seg3502.pms.representation.EmployeeRepresentation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class EmployeeAssembler : RepresentationModelAssemblerSupport<Employee, EmployeeRepresentation>(EmployeesController::class.java, EmployeeRepresentation::class.java) {
    override fun toModel(entity: Employee): EmployeeRepresentation {
        val employeeRepresentation = instantiateModel(entity)

        employeeRepresentation.id = entity.id
        employeeRepresentation.employeeNumber = entity.employeeNumber
        employeeRepresentation.emailAddress = entity.emailAddress
        employeeRepresentation.firstName = entity.firstName
        employeeRepresentation.lastName = entity.lastName
        employeeRepresentation.type = entity::class.simpleName

        if (entity is Doctor) {
            employeeRepresentation.division = divisionRepresentation(entity.division)
        }

        if (entity is ChiefNurse) {
            employeeRepresentation.phoneExtension = entity.phoneExtension
            employeeRepresentation.bipperExtension = entity.bipperExtension
        }

        return employeeRepresentation
    }

    private fun divisionRepresentation(division: Division): DivisionRepresentation {
        val representation = DivisionRepresentation()

        representation.id = division.id
        representation.divisionNumber = division.divisionNumber
        representation.divisionName = division.divisionName
        representation.location = division.location
        representation.numberOfBeds = division.numberOfBeds
        representation.phoneExtension = division.phoneExtension
        representation.availability = division.availability

        return representation
    }
}