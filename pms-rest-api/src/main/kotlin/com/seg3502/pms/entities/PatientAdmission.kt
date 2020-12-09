package com.seg3502.pms.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class PatientAdmission() {
    constructor(patient: Patient, doctor: Doctor, roomNumber: String, bedNumber: String, privateInsuranceNumber: String? = null) : this() {
        this.patient = patient
        this.doctor = doctor
        this.roomNumber = roomNumber
        this.bedNumber = bedNumber
        this.privateInsuranceNumber = privateInsuranceNumber
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @ManyToOne
    var patient: Patient = Patient()

    @ManyToOne
    var doctor: Doctor = Doctor()

    @NotBlank
    var roomNumber: String = ""

    @NotBlank
    var bedNumber: String = ""

    var privateInsuranceNumber: String? = null
}