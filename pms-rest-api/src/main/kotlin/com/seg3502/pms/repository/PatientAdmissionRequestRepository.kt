package com.seg3502.pms.repository

import com.seg3502.pms.entities.PatientAdmissionRequest
import org.springframework.data.repository.CrudRepository

interface PatientAdmissionRequestRepository : CrudRepository<PatientAdmissionRequest, Long>
