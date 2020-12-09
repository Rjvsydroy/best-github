package com.seg3502.pms.entities

import java.time.LocalTime
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class PrescriptionSchedule() {
    constructor(timeOfDay: LocalTime, unitsToAdminister: Int, prescription: Prescription) : this() {
        this.timeOfDay = timeOfDay
        this.unitsToAdminister = unitsToAdminister
        this.prescription = prescription
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var timeOfDay: LocalTime = LocalTime.MIN

    @Min(1)
    var unitsToAdminister: Int = 0

    @ManyToOne
    var prescription: Prescription = Prescription()
}