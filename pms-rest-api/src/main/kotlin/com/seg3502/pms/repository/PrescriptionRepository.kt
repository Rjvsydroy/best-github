package com.seg3502.pms.repository

import com.seg3502.pms.entities.Prescription
import org.springframework.data.repository.CrudRepository

interface PrescriptionRepository : CrudRepository<Prescription, Long>
