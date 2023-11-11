package com.example.workflow.domain.entities

import java.util.UUID
import java.util.regex.Pattern

data class Employee(
    val id: UUID = UUID.randomUUID(),
    var employeeId: EmployeeID,
    var name: EmployeeName,
    var surname: EmployeeSurname,
    var schedule: EmployeeSchedule,
    var workHours: EmployeeWorkHours,
    var workedHours: EmployeeWorkedHours,
    var email : Email,


){

    override fun toString(): String{
        return "Employee { ID= $id" +
                "EmployeeID= $employeeId, " +
                "Name= $name, " +
                "Surname= $surname"
    }
}


@JvmInline
value class EmployeeID(private val id : String){
    init {
        require(
            Pattern.compile("\\d{8}").matcher(id).matches()
        ){
            "The ID must have 8 numbers."
        }
    }

    override fun toString(): String {
        return "EmployeeId= $id"
    }
}

@JvmInline
value class EmployeeName(private val name : String){
    init {
        require(
            name.length in 2..20
        ){
            "The name of the employee must be between 2 ann 20 characters"
        }

        require(
            Pattern.compile("\\D").matcher(name).matches()
        ){
            "The name can not have numbers."
        }
    }

    override fun toString(): String {
        return "EmployeeName= $name"
    }
}

@JvmInline
value class EmployeeSurname(private val surname : String){

    init {
        require(
        surname.length in 2..20
        ){
            "The name of the employee must be between 2 ann 20 characters"
        }

        require(
        Pattern.compile("\\D").matcher(surname).matches()
        ){
            "The name can not have numbers."
        }
    }

    override fun toString(): String{
        return "EmployeeSurname= $surname"
    }
}

@JvmInline
value class EmployeeSchedule(private val schedule : Map<String, Int>){
    init {
        require(schedule.size in 0..12){
            "Work hours must be between 0 and 12."
        }
    }

    override fun toString(): String {
        return "EmployeeSchedule= $schedule"
    }
}

@JvmInline
value class EmployeeWorkHours(private val workHours: Int){
    init {
        require(workHours in 0..12){
            "Work hours must be between 0 and 12."
        }
    }

    override fun toString(): String {
        return "WorkHours= $workHours"
    }


}

@JvmInline
value class EmployeeWorkedHours(private val workedHours: Int){
    init {
        require(workedHours in 0..12){
            "Work hours must be between 0 and 12."
        }
    }

    override fun toString(): String {
        return "WorkHours= $workedHours"
    }
}

@JvmInline
value class Email(private val email : String){
    init {
        require(
            Pattern.compile("^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}\$\n").matcher(email).matches()
        ){
            "The email do not have a right format.(example@domain.com "
        }
    }

    override fun toString(): String {
        return "Email= $email"
    }
}

@JvmInline
value class Phone(private val phone : String){
    init {
        require(
            Pattern.compile("^\\+(?:\\d{1,3})?[-.\\s]?\\d{10}\$\n").matcher(phone).matches()
        )
    }


    override fun toString(): String {
        return "Phone= $phone"
    }
}