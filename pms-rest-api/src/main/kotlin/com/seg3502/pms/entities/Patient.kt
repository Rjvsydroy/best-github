package com.seg3502.pms.entities

import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.*
import kotlin.collections.ArrayList

@Entity
class Patient() {
    constructor(insuranceNumber: String, firstName: String, lastName: String, address: String, phoneNumber: String, dateOfBirth: LocalDate, externalDoctor: String, sex: Sex, maritalStatus: MaritalStatus) : this() {
        this.insuranceNumber = insuranceNumber
        this.firstName = firstName
        this.lastName = lastName
        this.address = address
        this.phoneNumber = phoneNumber
        this.dateOfBirth = dateOfBirth
        this.externalDoctor = externalDoctor
        this.sex = sex
        this.maritalStatus = maritalStatus
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @NotEmpty
    var insuranceNumber: String = ""

    @NotEmpty
    var firstName: String = ""

    @NotEmpty
    var lastName: String = ""

    @NotEmpty
    var address: String = ""

    @NotEmpty
    var phoneNumber: String = ""

    var dateOfBirth: LocalDate = LocalDate.MIN

    @NotEmpty
    var externalDoctor: String = ""

    @Enumerated(EnumType.STRING)
    var sex: Sex = Sex.OTHER

    @Enumerated(EnumType.STRING)
    var maritalStatus: MaritalStatus = MaritalStatus.OTHER

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "patient")
    var closeRelatives: MutableList<CloseRelative> = ArrayList()

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "patient")
    var admissions: MutableList<PatientAdmission> = ArrayList()

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "patient")
    var admissionRequests: MutableList<PatientAdmissionRequest> = ArrayList()

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "patient")
    var prescriptions: MutableList<Prescription> = ArrayList()
}