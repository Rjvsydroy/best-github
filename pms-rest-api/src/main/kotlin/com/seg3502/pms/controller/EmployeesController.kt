package com.seg3502.pms.controller

import com.seg3502.pms.assemblers.EmployeeAssembler
import com.seg3502.pms.controller.payload.CreateChiefNurseData
import com.seg3502.pms.controller.payload.CreateDoctorData
import com.seg3502.pms.controller.payload.CreateEmployeeData
import com.seg3502.pms.entities.ChiefNurse
import com.seg3502.pms.entities.Doctor
import com.seg3502.pms.entities.Employee
import com.seg3502.pms.repository.ChiefNurseRepository
import com.seg3502.pms.repository.DivisionRepository
import com.seg3502.pms.repository.DoctorRepository
import com.seg3502.pms.repository.EmployeeRepository
import com.seg3502.pms.representation.EmployeeRepresentation
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.ValidationException

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("api/employees", produces = ["application/hal+json"])
class EmployeesController(private val employeeRepository: EmployeeRepository,
                          private val doctorRepository: DoctorRepository,
                          private val chiefNurseRepository: ChiefNurseRepository,
                          private val divisionRepository: DivisionRepository,
                          private val employeeAssembler: EmployeeAssembler,
                          private val passwordEncoder: PasswordEncoder) {
    @GetMapping
    fun getAllEmployees() : ResponseEntity<CollectionModel<EmployeeRepresentation>> {
        val employees = employeeRepository.findAll()
        return ResponseEntity.ok(employeeAssembler.toCollectionModel(employees))
    }

    @GetMapping("/doctors")
    fun getDoctors() : ResponseEntity<CollectionModel<EmployeeRepresentation>> {
        val doctors = doctorRepository.findAll()
        return ResponseEntity.ok(employeeAssembler.toCollectionModel(doctors))
    }

    @GetMapping("/chief-nurses")
    fun getChiefNurses() : ResponseEntity<CollectionModel<EmployeeRepresentation>> {
        val chiefNurses = chiefNurseRepository.findAll()
        return ResponseEntity.ok(employeeAssembler.toCollectionModel(chiefNurses))
    }

    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable id: Long): ResponseEntity<EmployeeRepresentation> {
        return employeeRepository.findById(id)
            .map { employee: Employee -> ResponseEntity.ok(employeeAssembler.toModel(employee)) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun createEmployee(@RequestBody employee: CreateEmployeeData) : ResponseEntity<EmployeeRepresentation> {
        try {
            val newEmployee = employeeRepository.save(Employee(
                employee.employeeNumber,
                employee.emailAddress,
                passwordEncoder.encode(employee.password),
                employee.firstName,
                employee.lastName
            ))

            val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEmployee.id)
                .toUri()

            return ResponseEntity.created(location).body(employeeAssembler.toModel(newEmployee))
        } catch (e: TransactionSystemException) {
            if (e.cause?.cause is ValidationException) {
                return ResponseEntity.badRequest().build()
            } else {
                throw e
            }
        }
    }

    @PostMapping("/doctors")
    fun createDoctor(@RequestBody doctor: CreateDoctorData) : ResponseEntity<EmployeeRepresentation> {
        try {
            val division = divisionRepository.findById(doctor.divisionId)

            if (division.isEmpty) {
                return ResponseEntity.notFound().build()
            }

            val newEmployee = doctorRepository.save(Doctor(
                doctor.employeeNumber,
                doctor.emailAddress,
                passwordEncoder.encode(doctor.password),
                doctor.firstName,
                doctor.lastName,
                division.get()
            ))

            val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEmployee.id)
                .toUri()

            return ResponseEntity.created(location).body(employeeAssembler.toModel(newEmployee))
        } catch (e: TransactionSystemException) {
            if (e.cause?.cause is ValidationException) {
                return ResponseEntity.badRequest().build()
            } else {
                throw e
            }
        }
    }

    @PostMapping("/chief-nurses")
    fun createChiefNurse(@RequestBody chiefNurse: CreateChiefNurseData) : ResponseEntity<EmployeeRepresentation> {
        try {
            val newEmployee = chiefNurseRepository.save(ChiefNurse(
                chiefNurse.employeeNumber,
                chiefNurse.emailAddress,
                passwordEncoder.encode(chiefNurse.password),
                chiefNurse.firstName,
                chiefNurse.lastName,
                chiefNurse.phoneExtension,
                chiefNurse.bipperExtension
            ))

            val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEmployee.id)
                .toUri()

            return ResponseEntity.created(location).body(employeeAssembler.toModel(newEmployee))
        } catch (e: TransactionSystemException) {
            if (e.cause?.cause is ValidationException) {
                return ResponseEntity.badRequest().build()
            } else {
                throw e
            }
        }
    }
}