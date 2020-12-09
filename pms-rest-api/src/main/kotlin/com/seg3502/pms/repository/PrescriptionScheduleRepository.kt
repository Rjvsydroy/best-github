package com.seg3502.pms.repository

import com.seg3502.pms.entities.PrescriptionSchedule
import org.springframework.data.repository.CrudRepository

interface PrescriptionScheduleRepository : CrudRepository<PrescriptionSchedule, Long>
