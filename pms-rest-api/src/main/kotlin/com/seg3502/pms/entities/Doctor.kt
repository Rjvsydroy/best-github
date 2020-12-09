package com.seg3502.pms.entities

import javax.persistence.*

@Entity
class Doctor : Employee {
    constructor() : super()
    constructor(employeeNumber: String, emailAddress: String, password: String, firstName: String, lastName: String, division: Division) : super(employeeNumber, emailAddress, password, firstName, lastName) {
        this.division = division
    }

    @ManyToOne
    var division: Division = Division()
}