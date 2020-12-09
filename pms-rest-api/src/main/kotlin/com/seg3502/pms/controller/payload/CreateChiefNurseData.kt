package com.seg3502.pms.controller.payload

data class CreateChiefNurseData(val employeeNumber: String, val emailAddress: String, val password: String, val firstName: String, val lastName: String, val phoneExtension: Int, val bipperExtension: Int)