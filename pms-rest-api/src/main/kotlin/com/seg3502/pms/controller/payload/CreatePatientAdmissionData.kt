package com.seg3502.pms.controller.payload

class CreatePatientAdmissionData(val doctorId: Long, val roomNumber: String, val bedNumber: String, val privateInsuranceNumber: String? = null)