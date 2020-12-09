package com.seg3502.pms.entities

import javax.persistence.*
import javax.validation.constraints.*

@Entity
class CloseRelative() {
    constructor(firstName: String, lastName: String, address: String, relationship: String, phoneNumber: String, patient: Patient) : this() {
        this.firstName = firstName
        this.lastName = lastName
        this.address = address
        this.relationship = relationship
        this.phoneNumber = phoneNumber
        this.patient = patient
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @NotEmpty
    var firstName: String = ""

    @NotEmpty
    var lastName: String = ""

    @NotEmpty
    var address: String = ""

    @NotEmpty
    var relationship: String = ""

    @NotEmpty
    var phoneNumber: String = ""

    @ManyToOne
    var patient: Patient = Patient()
}