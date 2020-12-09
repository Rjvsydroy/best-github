package com.seg3502.pms.representation

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "prescriptions")
@JsonInclude(JsonInclude.Include.NON_NULL)
class PrescriptionRepresentation : RepresentationModel<PrescriptionRepresentation>() {
    var id: Long = 0
    var patient: PatientRepresentation = PatientRepresentation()
    var medicationNumber: String = ""
    var medicationName: String = ""
    var unitsPerDay: Int = 0
    var administrationsPerDay: Int = 0
    var schedule: List<PrescriptionScheduleRepresentation> = ArrayList()
}