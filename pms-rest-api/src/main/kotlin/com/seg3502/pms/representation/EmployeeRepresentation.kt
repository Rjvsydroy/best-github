package com.seg3502.pms.representation

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "employees")
@JsonInclude(JsonInclude.Include.NON_NULL)
class EmployeeRepresentation : RepresentationModel<EmployeeRepresentation>() {
    var id: Long = 0
    var type: String? = ""
    var employeeNumber: String = ""
    var emailAddress: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var phoneExtension: Int? = null
    var bipperExtension: Int? = null
    var division: DivisionRepresentation? = null
}