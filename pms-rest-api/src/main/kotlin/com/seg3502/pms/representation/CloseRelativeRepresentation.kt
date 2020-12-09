package com.seg3502.pms.representation

import com.fasterxml.jackson.annotation.JsonInclude
import com.seg3502.pms.entities.Patient
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "patients")
@JsonInclude(JsonInclude.Include.NON_NULL)
class CloseRelativeRepresentation : RepresentationModel<CloseRelativeRepresentation>() {
    var id: Long = 0
    var firstName: String = ""
    var lastName: String = ""
    var address: String = ""
    var relationship: String = ""
    var phoneNumber: String = ""
    var patient: PatientRepresentation? = null
}