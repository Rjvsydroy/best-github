package com.seg3502.pms.repository

import com.seg3502.pms.entities.Employee
import javax.transaction.Transactional

@Transactional
interface EmployeeRepository : EmployeeBaseRepository<Employee>