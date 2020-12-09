package com.seg3502.pms.entities

import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

@Entity
class PatientAdmissionRequest() {
    constructor(requestReason: String, priority: Int, patient: Patient, doctor: Doctor) : this() {
        this.requestReason = requestReason
        this.priority = priority
        this.patient = patient
        this.doctor = doctor
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @NotEmpty
    var requestReason: String = ""

    @Min(1)
    @Max(10)
    var priority: Int = 0

    @ManyToOne
    var patient: Patient = Patient()

    @ManyToOne
    var doctor: Doctor = Doctor()
}