package com.example.workflow.domain.entities

import java.util.TreeSet
import java.util.UUID
import java.util.regex.Pattern

data class Employee(
    var id: String = UUID.randomUUID().toString(),
    var employeeId: EmployeeID,
    var name: EmployeeName,
    var surname: EmployeeSurname,
    var schedule: EmployeeSchedule = EmployeeSchedule(schedule = TreeSet<Task>(TaskComparator())),
    var workHours: EmployeeWorkHours,
    var workedHours: EmployeeWorkedHours = EmployeeWorkedHours(0),
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
value class EmployeeID(val id : String){
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
value class EmployeeName(val name : String){
    init {
        require(
            name.length in 2..20
        ){
            "The name of the employee must be between 2 ann 20 characters"
        }
        println("Longitud de la cadena: ${name.length}")
        println("Contenido de la cadena: $name")
        require(
            Pattern.compile("[a-zA-Z]+").matcher(name).matches()
        ){
            "The name can not have numbers."
        }
    }

    override fun toString(): String {
        return "EmployeeName= $name"
    }
}

@JvmInline
value class EmployeeSurname(val surname : String){

    init {
        require(
        surname.length in 2..20
        ){
            "The surname of the employee must be between 2 ann 20 characters"
        }

        require(
        Pattern.compile("[a-zA-Z]+").matcher(surname).matches()
        ){
            "The surname can not have numbers."
        }
    }

    override fun toString(): String{
        return "EmployeeSurname= $surname"
    }
}

@JvmInline
value class EmployeeSchedule(val schedule : TreeSet<Task>){
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
value class EmployeeWorkHours(val workHours: Int){
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
value class EmployeeWorkedHours(val workedHours: Int){
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
value class Email(val email : String){
    init {
        require(
            Pattern.compile("[a-zA-Z0-9.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}").matcher(email).matches()
        ){
            "The email do not have a right format.(example@domain.com "
        }
    }

    override fun toString(): String {
        return "Email= $email"
    }
}

@JvmInline
value class Phone(val phone : String){
    init {
        require(
            Pattern.compile("^\\+(?:\\d{1,3})?[-.\\s]?\\d{10}\$\n").matcher(phone).matches()
        )
    }


    override fun toString(): String {
        return "Phone= $phone"
    }
}
class TaskComparator : Comparator<Task>{


    override fun compare(o1: Task?, o2: Task?): Int {
        TODO("Compare of tasks")
    }

}