package com.seg3502.pms.authentication

import com.fasterxml.jackson.annotation.JsonIgnore
import com.seg3502.pms.entities.Employee
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class PmsUserDetails(val id: Long, private val emailAddress: String,
                     @JsonIgnore private val password: String,
                     private val authorities: MutableCollection<GrantedAuthority>) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return emailAddress
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

fun build(employee: Employee) : PmsUserDetails {
    val authorities = ArrayList<GrantedAuthority>()
    authorities.add(SimpleGrantedAuthority(employee::class.simpleName))

    return PmsUserDetails(
        employee.id,
        employee.emailAddress,
        employee.password,
        authorities
    )
}