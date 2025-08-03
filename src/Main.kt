import java.time.DateTimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    val employeeManager= EmployeeManager()

    while (true) {
        println("1.Add Employee")
        println("2.Delete Employee")
        println("3.Update Employee")
        println("4.Get Employees List")
        println("5.Check In")
        println("6.Check Out")
        println("7.Get Check-In List")
        println("8.Get incomplete Attendances")
        println("9.Exit")
        print("Choose an option: ")
        when (readln().toIntOrNull()) {
            1->{
                addEmployee(employeeManager)
            }
            2->{
                deleteEmployee(employeeManager)
            }
            3->{
                updateEmployee(employeeManager)
            }
            4 -> {
                println("----- Employee List -----")
                val employeeList=employeeManager.getEmployeesList()
                if (employeeList.isEmpty()){
                    println("No Employees yet added")
                }
                else{
                    employeeList.forEach{ println(it)}
                }
            }
            5 -> {
               checkIn(employeeManager)
            }
            6 -> {
                checkOut(employeeManager)
            }
            7 -> {
                println("----- Check-In List -----")
                val checkInList=employeeManager.getAttendanceList()
                if (checkInList.isEmpty()){
                    println("No Check-ins yet")
                }
                else{
                    checkInList.forEach{ println(it)}
                }
            }
            8->{
                println("Incomplete attendances")
                val incompleteAttendances=employeeManager.getIncompleteAttendances()
                if(incompleteAttendances.isEmpty()){
                    println("No incomplete attendances")
                }
                else{
                    incompleteAttendances.forEach{println(it)}
                }
            }
            9->{
                break
            }
            else -> println("Invalid option")
        }
        println()
    }
}


fun addEmployee(employeeManager: EmployeeManager){
    println("First name: ")
    val empFirstName=readln()
    println("Last name: ")
    val empLastName=readln()
    println("Role: ")
    val empRole=readln()
    println("Reporting to: ")
    val reportingTo=readln()
    val employee=Employee(empFirstName,empLastName,Role.from(empRole),Manager.from(reportingTo))
    if(employee.validate()){
        employeeManager.addEmployee(employee)
        println(employee)
        println("Employee added successfully!")
    }
    else{
        println(employee.getErrorMessage())
        println("Failed to add employee")
    }
}

fun deleteEmployee(employeeManager: EmployeeManager){
    println("Enter employee ID to be deleted")
    val empId=readln()
    if(employeeManager.deleteEmployee(empId)){
        println("Employee deleted successfully!")
    }
    else{
        println("Employee ID not found. Failed to delete")
    }
}

fun updateEmployee(employeeManager: EmployeeManager){
    println("Enter employee id to be updated")
    val empId=readln()
    println("First name: ")
    val empFirstName=readln()
    println("Last name: ")
    val empLastName=readln()
    println("Role: ")
    val empRole=readln()
    println("Reporting to: ")
    val reportingTo=readln()
    if(employeeManager.updateEmployee(empId,empFirstName,empLastName,empRole,reportingTo)){
        println("Employee details updated successfully!")
    }
    else{
        println("Failed to update details")
    }
}

fun checkIn(employeeManager: EmployeeManager){
    print("Enter employee ID: ")
    val empId = readln()

    val inputDateTime = getDateTimeFromUserOrNow()
    if (inputDateTime == null) {
        println("Invalid dateTime")
        return
    }

    val checkInStatus = employeeManager.checkIn(empId, inputDateTime)
    if (checkInStatus) {
        val employee: Employee?= employeeManager.getEmployee(empId)
        val formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        println("Check-in Successful! Employee Id: $empId Name: ${employee?.firstName} ${employee?.lastName} DateTime: ${inputDateTime.format(formatter)}")
    } else {
        println("Check-in Failed.") //Either user already checked-in or invalid user id
    }
}

fun checkOut(employeeManager: EmployeeManager){
    print("Enter Employee ID: ")
    val empId = readln()

    val inputDateTime = getDateTimeFromUserOrNow()
    if (inputDateTime == null) {
        println("Invalid dateTime")
        return
    }

    val checkOutResult = employeeManager.checkOut(empId, inputDateTime)
    if (checkOutResult != null) {
        println("Check out successful")
        println(checkOutResult)
    } else {
        println("Check-out Failed.")
    }
}

fun getDateTimeFromUserOrNow(): LocalDateTime? {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    print("Enter date and time (dd-MM-yyyy HH:mm) : ")
    val input = readln().trim()
    return if (input.isEmpty()) {
        LocalDateTime.now()
    } else {
        try {
            val dateTime = LocalDateTime.parse(input, formatter)
            if (dateTime.isAfter(LocalDateTime.now())) {  //Future date time
                null
            } else dateTime
        } catch ( e:DateTimeException) {  //Invalid format
            null
        }
    }
}