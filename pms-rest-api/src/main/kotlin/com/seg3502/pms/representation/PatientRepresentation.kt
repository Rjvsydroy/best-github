package com.seg3502.pms.representation

import com.fasterxml.jackson.annotation.JsonInclude
import com.seg3502.pms.entities.MaritalStatus
import com.seg3502.pms.entities.Sex
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import java.time.LocalDate

@Relation(collectionRelation = "patients")
@JsonInclude(JsonInclude.Include.NON_NULL)
class PatientRepresentation : RepresentationModel<EmployeeRepresentation>() {
    var id: Long = 0
    var insuranceNumber: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var address: String = ""
    var phoneNumber: String = ""
    var dateOfBirth: LocalDate = LocalDate.MIN
    var externalDoctor: String = ""
    var sex: Sex = Sex.OTHER
    var maritalStatus: MaritalStatus = MaritalStatus.OTHER
    var closeRelatives: List<CloseRelativeRepresentation>? = null
}