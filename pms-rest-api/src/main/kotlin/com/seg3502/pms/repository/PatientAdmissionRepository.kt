package com.seg3502.pms.repository

import com.seg3502.pms.entities.PatientAdmission
import org.springframework.data.repository.CrudRepository

interface PatientAdmissionRepository : CrudRepository<PatientAdmission, Long>
