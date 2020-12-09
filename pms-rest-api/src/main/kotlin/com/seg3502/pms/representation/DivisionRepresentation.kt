package com.seg3502.pms.representation

import com.fasterxml.jackson.annotation.JsonInclude
import com.seg3502.pms.entities.DivisionAvailability
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "divisions")
@JsonInclude(JsonInclude.Include.NON_NULL)
class DivisionRepresentation : RepresentationModel<DivisionRepresentation>() {
    var id: Long = 0
    var divisionNumber: String = ""
    var divisionName: String = ""
    var location: String = ""
    var numberOfBeds: Int = 0
    var phoneExtension: Int = 0
    var availability: DivisionAvailability = DivisionAvailability.AVAILABLE
    var chiefNurse: EmployeeRepresentation? = null
    var doctors: List<EmployeeRepresentation>? = null
}