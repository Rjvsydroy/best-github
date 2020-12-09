package com.seg3502.pms.controller

import com.seg3502.pms.assemblers.CloseRelativeAssembler
import com.seg3502.pms.assemblers.PatientAdmissionAssembler
import com.seg3502.pms.assemblers.PatientAdmissionRequestAssembler
import com.seg3502.pms.assemblers.PatientAssembler
import com.seg3502.pms.controller.payload.AdmitFromPatientAdmissionRequestData
import com.seg3502.pms.controller.payload.CreatePatientAdmissionData
import com.seg3502.pms.controller.payload.CreatePatientAdmissionRequestData
import com.seg3502.pms.entities.CloseRelative
import com.seg3502.pms.entities.Patient
import com.seg3502.pms.entities.PatientAdmission
import com.seg3502.pms.entities.PatientAdmissionRequest
import com.seg3502.pms.repository.*
import com.seg3502.pms.representation.CloseRelativeRepresentation
import com.seg3502.pms.representation.PatientAdmissionRepresentation
import com.seg3502.pms.representation.PatientAdmissionRequestRepresentation
import com.seg3502.pms.representation.PatientRepresentation
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("api/patients", produces = ["application/hal+json"])
class PatientsController(private val patientRepository: PatientRepository,
                         private val closeRelativeRepository: CloseRelativeRepository,
                         private val doctorRepository: DoctorRepository,
                         private val patientAdmissionRepository: PatientAdmissionRepository,
                         private val patientAdmissionRequestRepository: PatientAdmissionRequestRepository,
                         private val patientAssembler: PatientAssembler,
                         private val closeRelativeAssembler: CloseRelativeAssembler,
                         private val patientAdmissionAssembler: PatientAdmissionAssembler,
                         private val patientAdmissionRequestAssembler: PatientAdmissionRequestAssembler) {
    @GetMapping
    fun getPatients(): ResponseEntity<CollectionModel<PatientRepresentation>> {
        return ResponseEntity.ok(patientAssembler.toCollectionModel(patientRepository.findAll()))
    }

    @GetMapping("/{id}")
    fun getPatientById(@PathVariable id: Long): ResponseEntity<PatientRepresentation> {
        return patientRepository.findById(id)
            .map { p -> ResponseEntity.ok(patientAssembler.toModel(p)) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun createPatient(@RequestBody patient: Patient): ResponseEntity<PatientRepresentation> {
        val newPatient = patientRepository.save(patient)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newPatient.id)
            .toUri()

        return ResponseEntity.created(location).body(patientAssembler.toModel(patient))
    }

    @PatchMapping("/{id}")
    fun updatePatient(@PathVariable id: Long, @RequestBody updated: Patient): ResponseEntity<PatientRepresentation> {
        return patientRepository.findById(id).map { patient ->
            patient.insuranceNumber = updated.insuranceNumber
            patient.firstName = updated.firstName
            patient.lastName = updated.lastName
            patient.address = updated.address
            patient.phoneNumber = updated.phoneNumber
            patient.dateOfBirth = updated.dateOfBirth
            patient.externalDoctor = updated.externalDoctor
            patient.sex = updated.sex
            patient.maritalStatus = updated.maritalStatus

            patientRepository.save(patient)

            ResponseEntity.ok(patientAssembler.toModel(patient))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deletePatient(@PathVariable id: Long): ResponseEntity<Any> {
        patientRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/close-relatives")
    fun addCloseRelative(@PathVariable id: Long, @RequestBody closeRelative: CloseRelative): ResponseEntity<CloseRelativeRepresentation> {
        return patientRepository.findById(id).map { patient ->
            val newCloseRelative = closeRelativeRepository.save(closeRelative)
            val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCloseRelative.id)
                .toUri()

            ResponseEntity.created(location).body(closeRelativeAssembler.toModel(newCloseRelative))
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/{patientId}/close-relatives/{id}")
    fun getCloseRelativeById(@PathVariable patientId: Long, @PathVariable id: Long): ResponseEntity<CloseRelativeRepresentation> {
        val closeRelative = closeRelativeRepository.findById(id)

        if (closeRelative.isEmpty || closeRelative.get().patient.id != patientId) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(closeRelativeAssembler.toModel(closeRelative.get()))
    }

    @DeleteMapping("/{patientId}/close-relatives/{id}")
    fun deleteCloseRelative(@PathVariable patientId: Long, @PathVariable id: Long): ResponseEntity<Any> {
        closeRelativeRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/admission-requests")
    fun requestAdmission(@PathVariable id: Long, @RequestBody data: CreatePatientAdmissionRequestData): ResponseEntity<PatientAdmissionRequestRepresentation> {
        return patientRepository.findById(id).map { patient ->
            doctorRepository.findById(data.doctorId).map { doctor ->
                val request = PatientAdmissionRequest()

                request.requestReason = data.requestReason
                request.priority = data.priority
                request.patient = patient
                request.doctor = doctor

                val newRequest = patientAdmissionRequestRepository.save(request)
                val location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newRequest.id)
                    .toUri()

                ResponseEntity.created(location).body(patientAdmissionRequestAssembler.toModel(newRequest))
            }.orElse(ResponseEntity.notFound().build())
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping("/{patientId}/admission-requests/{id}/admit")
    fun admitFromAdmissionRequest(@PathVariable patientId: Long, @PathVariable id: Long, @RequestBody data: AdmitFromPatientAdmissionRequestData): ResponseEntity<PatientAdmissionRepresentation> {
        return patientAdmissionRequestRepository.findById(id).map { admissionRequest ->
            val admission = PatientAdmission()

            admission.roomNumber = data.roomNumber
            admission.bedNumber = data.bedNumber
            admission.privateInsuranceNumber = data.privateInsuranceNumber
            admission.patient = admissionRequest.patient
            admission.doctor = admissionRequest.doctor

            val newAdmission = patientAdmissionRepository.save(admission)
            val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAdmission.id)
                .toUri()

            patientAdmissionRequestRepository.delete(admissionRequest)

            ResponseEntity.created(location).body(patientAdmissionAssembler.toModel(newAdmission))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{patientId}/admission-requests/{id}")
    fun deleteAdmissionRequest(@PathVariable patientId: Long, @PathVariable id: Long): ResponseEntity<Any> {
        patientAdmissionRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/admit")
    fun admitPatient(@PathVariable id: Long, @RequestBody data: CreatePatientAdmissionData): ResponseEntity<PatientAdmissionRepresentation> {
        return patientRepository.findById(id).map { patient ->
            doctorRepository.findById(data.doctorId).map { doctor ->
                val admission = PatientAdmission()

                admission.roomNumber = data.roomNumber
                admission.bedNumber = data.bedNumber
                admission.privateInsuranceNumber = data.privateInsuranceNumber
                admission.patient = patient
                admission.doctor = doctor

                val newAdmission = patientAdmissionRepository.save(admission)
                val location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newAdmission.id)
                    .toUri()

                ResponseEntity.created(location).body(patientAdmissionAssembler.toModel(newAdmission))
            }.orElse(ResponseEntity.notFound().build())
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{patientId}/admissions/{admissionId}")
    fun deleteAdmission(@PathVariable patientId: Long, @PathVariable admissionId: Long): ResponseEntity<Any> {
        patientAdmissionRepository.deleteById(admissionId)
        return ResponseEntity.noContent().build()
    }
}