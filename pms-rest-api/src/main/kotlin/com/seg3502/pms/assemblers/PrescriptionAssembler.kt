package com.seg3502.pms.assemblers

import com.seg3502.pms.controller.PatientsController
import com.seg3502.pms.controller.PrescriptionsController
import com.seg3502.pms.entities.Patient
import com.seg3502.pms.entities.Prescription
import com.seg3502.pms.entities.PrescriptionSchedule
import com.seg3502.pms.representation.PatientRepresentation
import com.seg3502.pms.representation.PrescriptionRepresentation
import com.seg3502.pms.representation.PrescriptionScheduleRepresentation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Component

@Component
class PrescriptionAssembler : RepresentationModelAssemblerSupport<Prescription, PrescriptionRepresentation>(PrescriptionsController::class.java, PrescriptionRepresentation::class.java) {
    override fun toModel(entity: Prescription): PrescriptionRepresentation {
        val representation = instantiateModel(entity)

        representation.id = entity.id
        representation.patient = patientRepresentation(entity.patient)
        representation.medicationNumber = entity.medicationNumber
        representation.medicationName = entity.medicationName
        representation.unitsPerDay = entity.schedule.sumOf { s -> s.unitsToAdminister }
        representation.administrationsPerDay = entity.schedule.count()
        representation.schedule = prescriptionScheduleRepresentations(entity.schedule)

        return representation
    }

    private fun patientRepresentation(patient: Patient): PatientRepresentation {
        val representation = PatientRepresentation()

        representation.id = patient.id
        representation.insuranceNumber = patient.insuranceNumber
        representation.firstName = patient.firstName
        representation.lastName = patient.lastName
        representation.address = patient.address
        representation.phoneNumber = patient.phoneNumber
        representation.dateOfBirth = patient.dateOfBirth
        representation.externalDoctor = patient.externalDoctor
        representation.sex = patient.sex
        representation.maritalStatus = patient.maritalStatus

        representation.add(linkTo(methodOn(PatientsController::class.java).getPatientById(patient.id)).withSelfRel())

        return representation
    }

    private fun prescriptionScheduleRepresentations(schedules: List<PrescriptionSchedule>): List<PrescriptionScheduleRepresentation> {
        return schedules.map { s -> prescriptionScheduleRepresentation(s) }
    }

    private fun prescriptionScheduleRepresentation(schedule: PrescriptionSchedule): PrescriptionScheduleRepresentation {
        val representation = PrescriptionScheduleRepresentation()

        representation.id = schedule.id
        representation.timeOfDay = schedule.timeOfDay
        representation.unitsToAdminister = schedule.unitsToAdminister

        return representation
    }
}