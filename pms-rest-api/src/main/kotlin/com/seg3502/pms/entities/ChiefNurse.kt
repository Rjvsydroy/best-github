package com.seg3502.pms.entities

import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
class ChiefNurse : Employee {
    constructor() : super()

    constructor(employeeNumber: String, emailAddress: String, password: String, firstName: String, lastName: String, phoneExtension: Int, bipperExtension: Int) : super(employeeNumber, emailAddress, password, firstName, lastName) {
        this.phoneExtension = phoneExtension
        this.bipperExtension = bipperExtension
    }

    @NotNull
    @Min(0)
    var phoneExtension: Int? = null

    @NotNull
    @Min(0)
    var bipperExtension: Int? = null
}