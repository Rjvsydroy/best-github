package com.seg3502.pms.repository

import com.seg3502.pms.entities.Employee
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

@NoRepositoryBean
interface EmployeeBaseRepository<T : Employee> : CrudRepository<T, Long> {
    fun findByEmailAddress(email: String): Optional<Employee>
    fun existsByEmailAddress(email: String): Boolean
}