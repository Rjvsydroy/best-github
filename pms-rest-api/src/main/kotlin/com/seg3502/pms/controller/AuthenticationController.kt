package com.seg3502.pms.controller

import com.seg3502.pms.authentication.JwtUtils
import com.seg3502.pms.authentication.PmsUserDetails
import com.seg3502.pms.controller.payload.AuthResponse
import com.seg3502.pms.controller.payload.SignInData
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("auth", produces = ["application/hal+json"])
class AuthenticationController(private val authenticationManager: AuthenticationManager,
                               private val jwtUtils: JwtUtils) {
    @PostMapping("sign-in")
    fun authenticateUser(@RequestBody request: @Valid SignInData): ResponseEntity<AuthResponse> {
        val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.emailAddress, request.password))

        SecurityContextHolder.getContext().authentication = authentication

        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as PmsUserDetails
        val role = userDetails.authorities.elementAt(0)

        return ResponseEntity.ok(AuthResponse(jwt, userDetails.id, userDetails.username, role.authority))
    }
}