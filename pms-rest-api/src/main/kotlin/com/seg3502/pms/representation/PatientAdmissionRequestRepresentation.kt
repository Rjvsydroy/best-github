package com.seg3502.pms.representation

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "patientAdmissionRequests")
@JsonInclude(JsonInclude.Include.NON_NULL)
class PatientAdmissionRequestRepresentation : RepresentationModel<PatientAdmissionRequestRepresentation>() {
    var id: Long = 0
    var requestReason: String = ""
    var priority: Int = 0
    var patient: PatientRepresentation = PatientRepresentation()
    var doctor: EmployeeRepresentation = EmployeeRepresentation()
}