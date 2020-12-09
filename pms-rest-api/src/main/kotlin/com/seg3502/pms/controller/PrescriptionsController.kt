package com.seg3502.pms.controller

import com.seg3502.pms.assemblers.PrescriptionAssembler
import com.seg3502.pms.controller.payload.CreatePrescriptionData
import com.seg3502.pms.entities.Prescription
import com.seg3502.pms.entities.PrescriptionSchedule
import com.seg3502.pms.repository.PatientRepository
import com.seg3502.pms.repository.PrescriptionRepository
import com.seg3502.pms.repository.PrescriptionScheduleRepository
import com.seg3502.pms.representation.PrescriptionRepresentation
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("api/prescriptions", produces = ["application/hal+json"])
class PrescriptionsController(private val patientRepository: PatientRepository,
                              private val prescriptionRepository: PrescriptionRepository,
                              private val prescriptionScheduleRepository: PrescriptionScheduleRepository,
                              private val prescriptionAssembler: PrescriptionAssembler) {
    @GetMapping
    fun getPrescriptions(): ResponseEntity<CollectionModel<PrescriptionRepresentation>> {
        return ResponseEntity.ok(prescriptionAssembler.toCollectionModel(prescriptionRepository.findAll()))
    }

    @PostMapping
    fun createPrescription(@RequestBody prescriptionData: CreatePrescriptionData): ResponseEntity<PrescriptionRepresentation> {
        return patientRepository.findById(prescriptionData.patientId).map { patient ->
            val prescription = Prescription()

            prescription.medicationNumber = prescriptionData.medicationNumber
            prescription.medicationName = prescriptionData.medicationName
            prescription.patient = patient

            val newPrescription = prescriptionRepository.save(prescription)
            val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPrescription.id)
                .toUri()

            ResponseEntity.created(location).body(prescriptionAssembler.toModel(prescription))
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/{id}")
    fun getPrescription(@PathVariable id: Long): ResponseEntity<PrescriptionRepresentation> {
        return prescriptionRepository.findById(id)
            .map { p -> ResponseEntity.ok(prescriptionAssembler.toModel(p)) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping("/{id}/schedule")
    fun createPrescriptionSchedule(@PathVariable id: Long, @RequestBody schedule: PrescriptionSchedule): ResponseEntity<PrescriptionRepresentation> {
        return prescriptionRepository.findById(id).map { prescription ->
            schedule.prescription = prescription

            val newSchedule = prescriptionScheduleRepository.save(schedule)
            val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSchedule.id)
                .toUri()

            ResponseEntity.created(location).body(prescriptionAssembler.toModel(prescription))
        }.orElse(ResponseEntity.notFound().build())
    }
}