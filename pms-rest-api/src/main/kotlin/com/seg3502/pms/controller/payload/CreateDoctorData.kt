package com.seg3502.pms.controller.payload

data class CreateDoctorData(val employeeNumber: String, val emailAddress: String, val password: String, val firstName: String, val lastName: String, val divisionId: Long)
