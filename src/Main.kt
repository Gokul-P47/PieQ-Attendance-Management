
fun main() {
    val employeeManager= EmployeeManager()

    while (true) {
        println("1.Add Employee")
        println("2.Delete Employee")
        println("3.Update Employee")
        println("4.Get Employees List")
        println("5.Check In")
        println("6.Check Out")
        println("7.Get Attendance List")
        println("8.Get Incomplete Attendances")
        println("9.Get Total Working Hours Between two Dates")
        println("10.Exit")
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
                getEmployeeList(employeeManager)
            }
            5 -> {
                checkIn(employeeManager)
            }
            6 -> {
                checkOut(employeeManager)
            }
            7 -> {
                getAttendanceList(employeeManager)
            }
            8->{
                getIncompleteAttendance(employeeManager)
            }
            9->{
                getTotalWorkingHrsBetweenDate(employeeManager)
            }
            10->{
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
    println(employeeManager.addEmployee(employee))
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

fun getEmployeeList(employeeManager: EmployeeManager){
    println("----- Employee List -----")
    val employeeList=employeeManager.getEmployeesList()
    if (employeeList.isEmpty()){
        println("No Employees yet added")
    }
    else{
        employeeList.forEach{ println(it)}
    }
}

fun checkIn(employeeManager: EmployeeManager){
    print("Enter employee ID: ")
    val empId = readln()

    print("Enter date and time (dd-MM-yyyy HH:mm) : ")
    val inputDateTime = readln().trim()


    val checkInResult = employeeManager.checkIn(empId, inputDateTime)
    if (checkInResult != null) {
        println("Check-in successful")
        println(checkInResult)
    } else {
        println("Check-in Failed.")  //Either user already checked-in or invalid user id
    }
}

fun checkOut(employeeManager: EmployeeManager){
    print("Enter Employee ID: ")
    val empId = readln()

    print("Enter date and time (dd-MM-yyyy HH:mm) : ")
    val inputDateTime = readln().trim()

    val checkOutResult = employeeManager.checkOut(empId, inputDateTime)
    if (checkOutResult != null) {
        println("Check out successful")
        println(checkOutResult)
    } else {
        println("Check out Failed.")
    }
}

fun getAttendanceList(employeeManager: EmployeeManager){
    println("----- Check-In List -----")
    val checkInList=employeeManager.getAttendanceList()
    if (checkInList.isEmpty()){
        println("No Check-ins yet")
    }
    else{
        checkInList.forEach{ println(it)}
    }
}

fun getIncompleteAttendance(employeeManager: EmployeeManager){
    println("Incomplete attendances")
    val incompleteAttendances=employeeManager.getIncompleteAttendances()
    if(incompleteAttendances.isEmpty()){
        println("No incomplete attendances")
    }
    else{
        incompleteAttendances.forEach{println(it)}
    }
}

fun getTotalWorkingHrsBetweenDate(employeeManager: EmployeeManager){
    print("Enter starting date(dd-MM-yyyy): ")
    val startingDate=readln().trim()
    print("Enter ending date(dd-MM-yyyy): ")
    val endingDate=readln().trim()

    val map=employeeManager.getTotalWorkingHrsBetween(startingDate,endingDate)
    if(map!=null) {
        println("Employee working hours")
        println("Employee Id | Working hours")
        map.forEach { println("${it.key}   →   ${it.value.toHours()}h ${it.value.toMinutesPart()}m") }
    }
}