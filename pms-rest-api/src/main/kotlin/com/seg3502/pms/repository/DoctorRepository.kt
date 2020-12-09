package com.seg3502.pms.repository

import com.seg3502.pms.entities.Doctor
import javax.transaction.Transactional

@Transactional
interface DoctorRepository : EmployeeBaseRepository<Doctor>