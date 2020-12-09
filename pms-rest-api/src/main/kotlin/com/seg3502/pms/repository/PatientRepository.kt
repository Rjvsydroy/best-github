package com.seg3502.pms.repository

import com.seg3502.pms.entities.Patient
import org.springframework.data.repository.CrudRepository

interface PatientRepository : CrudRepository<Patient, Long>
