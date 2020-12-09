package com.seg3502.pms.controller.payload

data class CreatePrescriptionData(val medicationNumber: String, val medicationName: String, val patientId: Long)
