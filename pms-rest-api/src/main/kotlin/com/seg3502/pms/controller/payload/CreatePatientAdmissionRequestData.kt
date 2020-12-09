package com.seg3502.pms.controller.payload

data class CreatePatientAdmissionRequestData(val requestReason: String, val priority: Int, val doctorId: Long)
