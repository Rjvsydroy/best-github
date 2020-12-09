package com.seg3502.pms.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Inheritance
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["emailAddress"])])
open class Employee() {
    constructor(employeeNumber: String, emailAddress: String, password: String, firstName: String, lastName: String) : this() {
        this.employeeNumber = employeeNumber
        this.emailAddress = emailAddress
        this.password = password
        this.firstName = firstName
        this.lastName = lastName
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0

    @NotBlank
    open var employeeNumber: String = ""

    @NotBlank
    open var emailAddress: String = ""

    @NotBlank
    @Size(max = 120)
    open var password: String = ""

    @NotBlank
    open var firstName: String = ""

    @NotBlank
    open var lastName: String = ""
}