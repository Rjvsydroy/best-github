package com.seg3502.pms.controller.payload

data class AdmitFromPatientAdmissionRequestData(val roomNumber: String, val bedNumber: String, val privateInsuranceNumber: String? = null)
