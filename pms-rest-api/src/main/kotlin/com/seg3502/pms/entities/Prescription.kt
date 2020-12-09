package com.seg3502.pms.entities

import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

@Entity
class Prescription() {
    constructor(patient: Patient, medicationNumber: String, medicationName: String) : this() {
        this.patient = patient
        this.medicationNumber = medicationNumber
        this.medicationName = medicationName
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @ManyToOne
    var patient: Patient = Patient()

    @NotEmpty
    var medicationNumber: String = ""

    @NotEmpty
    var medicationName: String = ""

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "prescription")
    var schedule: MutableList<PrescriptionSchedule> = ArrayList()
}