package com.seg3502.pms.repository

import com.seg3502.pms.entities.Division
import com.seg3502.pms.entities.PatientAdmission
import com.seg3502.pms.entities.PatientAdmissionRequest
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface DivisionRepository : CrudRepository<Division, Long> {
    @Query(value = "SELECT par FROM PatientAdmissionRequest par WHERE par.doctor.division.id = :divisionId")
    fun getAdmissionRequests(divisionId: Long): MutableList<PatientAdmissionRequest>

    @Query(value = "SELECT pa FROM PatientAdmission pa WHERE pa.doctor.division.id = :divisionId")
    fun getAdmissions(divisionId: Long): MutableList<PatientAdmission>
}
