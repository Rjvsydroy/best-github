package com.seg3502.pms.representation

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "patientAdmissions")
@JsonInclude(JsonInclude.Include.NON_NULL)
class PatientAdmissionRepresentation : RepresentationModel<PatientAdmissionRepresentation>() {
    var id: Long = 0
    var patient: PatientRepresentation = PatientRepresentation()
    var doctor: EmployeeRepresentation = EmployeeRepresentation()
    var roomNumber: String = ""
    var bedNumber: String = ""
    var privateInsuranceNumber: String? = null
}