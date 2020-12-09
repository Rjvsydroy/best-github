package com.seg3502.pms.controller

import com.seg3502.pms.assemblers.DivisionAssembler
import com.seg3502.pms.assemblers.PatientAdmissionAssembler
import com.seg3502.pms.assemblers.PatientAdmissionRequestAssembler
import com.seg3502.pms.entities.Division
import com.seg3502.pms.repository.DivisionRepository
import com.seg3502.pms.representation.DivisionRepresentation
import com.seg3502.pms.representation.PatientAdmissionRepresentation
import com.seg3502.pms.representation.PatientAdmissionRequestRepresentation
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("api/divisions", produces = ["application/hal+json"])
class DivisionsController(private val divisionRepository: DivisionRepository,
                          private val divisionAssembler: DivisionAssembler,
                          private val patientAdmissionAssembler: PatientAdmissionAssembler,
                          private val patientAdmissionRequestAssembler: PatientAdmissionRequestAssembler) {
    @GetMapping
    fun getDivisions() : ResponseEntity<CollectionModel<DivisionRepresentation>> {
        return ResponseEntity.ok(divisionAssembler.toCollectionModel(divisionRepository.findAll()))
    }

    @GetMapping("/{id}")
    fun getDivisionById(@PathVariable id: Long) : ResponseEntity<DivisionRepresentation> {
        return divisionRepository.findById(id)
            .map { division: Division -> ResponseEntity.ok(divisionAssembler.toModel(division)) }
            .orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/{id}/admission-requests")
    fun getAdmissionRequests(@PathVariable id: Long): ResponseEntity<CollectionModel<PatientAdmissionRequestRepresentation>> {
        return ResponseEntity.ok(patientAdmissionRequestAssembler.toCollectionModel(divisionRepository.getAdmissionRequests(id)))
    }

    @GetMapping("/{id}/admissions")
    fun getAdmissions(@PathVariable id: Long): ResponseEntity<CollectionModel<PatientAdmissionRepresentation>> {
        return ResponseEntity.ok(patientAdmissionAssembler.toCollectionModel(divisionRepository.getAdmissions(id)))
    }
}