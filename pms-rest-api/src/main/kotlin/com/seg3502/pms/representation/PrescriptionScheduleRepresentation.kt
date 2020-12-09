package com.seg3502.pms.representation

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import java.time.LocalTime

@Relation(collectionRelation = "prescriptionSchedules")
@JsonInclude(JsonInclude.Include.NON_NULL)
class PrescriptionScheduleRepresentation : RepresentationModel<PrescriptionScheduleRepresentation>() {
    var id: Long = 0
    var timeOfDay: LocalTime = LocalTime.MIN
    var unitsToAdminister: Int = 0
}