package com.seg3502.pms.entities

import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
class Division() {
    constructor(divisionNumber: String, divisionName: String, location: String, numberOfBeds: Int, phoneExtension: Int, chiefNurse: ChiefNurse) : this() {
        this.divisionNumber = divisionNumber
        this.divisionName = divisionName
        this.location = location
        this.numberOfBeds = numberOfBeds
        this.phoneExtension = phoneExtension
        this.chiefNurse = chiefNurse
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @NotBlank
    var divisionNumber: String = ""

    @NotBlank
    var divisionName: String = ""

    @NotBlank
    var location: String = ""

    @Min(1)
    var numberOfBeds: Int = 0

    @Min(0)
    var phoneExtension: Int = 0

    var availability: DivisionAvailability = DivisionAvailability.AVAILABLE

    @ManyToOne(fetch = FetchType.LAZY)
    var chiefNurse: ChiefNurse = ChiefNurse()

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "division")
    var doctors: MutableList<Doctor> = ArrayList()
}