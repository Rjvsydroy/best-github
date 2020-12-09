package com.seg3502.pms.authentication

import com.seg3502.pms.repository.EmployeeRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PmsUserDetailsService(private val employeeRepository: EmployeeRepository) : UserDetailsService {
    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val employee = employeeRepository.findByEmailAddress(username).orElseThrow { UsernameNotFoundException("User with email address $username not found") }
        return build(employee)
    }
}