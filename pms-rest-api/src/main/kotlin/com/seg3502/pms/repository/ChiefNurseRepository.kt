package com.seg3502.pms.repository

import com.seg3502.pms.entities.ChiefNurse
import javax.transaction.Transactional

@Transactional
interface ChiefNurseRepository : EmployeeBaseRepository<ChiefNurse>