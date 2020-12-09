package com.seg3502.pms.controller.payload

data class AuthResponse(val token: String, val id: Long, val username: String, val role: String)
