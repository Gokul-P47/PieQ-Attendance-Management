import java.time.DateTimeException
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

class EmployeeManager {
    companion object {
        val employeeList = EmployeeList() //To store employees
        private val checkInList = AttendanceList()  //To store attendance records
    }

    init {
        addInitialEmployees()
    }

    fun addInitialEmployees() {
        employeeList.addAll(
            listOf(
                Employee( "Gokul", "P", Role.DEVELOPER, Manager.PIEQ1001),
                Employee( "Mark", "Lee", Role.DEVELOPER, Manager.PIEQ1001),
                Employee( "Jack", "Lee", Role.INTERN, Manager.PIEQ1003),
                Employee( "Ashok", "Kumar", Role.DESIGNER, Manager.PIEQ1002),
                Employee( "Bob", "John", Role.DESIGNER,Manager.PIEQ1003),
                Employee("Tim", "David", Role.INTERN, Manager.PIEQ1003)
            )
        )
        employeeList.forEach { it.generateEmpId() }
    }

    //Add new Employee
    fun addEmployee(employee: Employee) :String{
        //Validate the employee object and add it to the list if it is valid
        if(employee.validate()){
            employee.generateEmpId()
            employeeList.add(employee)
            return "Employee Added SuccessFully!\n$employee"
        }
        return employee.getErrorMessage()+". Failed to add employee"
    }

    //Remove an employee
    fun deleteEmployee(empId: String): Boolean{
        val employee=employeeExists(empId)   //Check whether given employee id exists
        if(employee!=null){
            employeeList.remove(employee)
            return true
        }
        return false
    }

    //Update employee details
    fun updateEmployee(empId: String,empFirstName: String,empLastName: String,empRole: String,reportingTo: String):Boolean{
        val employee=getEmployee(empId)
        if(employee==null){
            println("Employee ID not found")
            return false
        }
        return employee.update(empFirstName,empLastName,empRole,reportingTo)
    }

    //Check whether employee exists with given empId
    fun employeeExists(empId: String): Employee? {
        return employeeList.find { it.id == empId }
    }

    fun getEmployeesList(): EmployeeList {
        return employeeList
    }

    fun checkIn(empId: String, checkInDateTime: String): String? {
        if (employeeExists(empId)==null){
            println("Employee ID not found")  //Employee not found with the given id
            return null
        }

        val parsedDateTime=parseDateTime(checkInDateTime)
        if (parsedDateTime == null) {
          println("Invalid dateTime")
            return null
        }

        if (hasAlreadyCheckedIn(empId, parsedDateTime)) {
            println("Employee has already checked in")
            return null     //Check whether the employee has already checked in today or not
        }

        checkInList.add(Attendance(empId, parsedDateTime))
        val employee: Employee?= getEmployee(empId)
        val formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        return "Employee Id: $empId Name: ${employee?.firstName} ${employee?.lastName} DateTime: ${parsedDateTime.format(formatter)}"
    }

    fun parseDateTime(inputDateTime: String): LocalDateTime?{
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

        return if (inputDateTime.isEmpty()) {
            LocalDateTime.now()
        } else {
            try {
                val dateTime = LocalDateTime.parse(inputDateTime, formatter)
                if (dateTime.isAfter(LocalDateTime.now())) {  //Future date time
                    null
                } else dateTime
            } catch ( e:DateTimeException) {  //Invalid format
                null
            }
        }
    }

    fun hasAlreadyCheckedIn(empId: String, inputDateTime: LocalDateTime): Boolean {
        return checkInList.any {         //check whether already checked in today or not
            it.id == empId && it.checkInDateTime.toLocalDate() == inputDateTime.toLocalDate()
        }
    }

    fun checkOut(empId: String,checkOutDateTime: String): String?{
        if (employeeExists(empId)==null){
            println("Employee ID not found")
            return null      //Employee not found with the given id
        }

        val parsedDateTime=parseDateTime(checkOutDateTime)
        if (parsedDateTime == null) {
            println("Invalid dateTime")
            return null
        }

        val attendance: Attendance?= validateCheckOut(empId,parsedDateTime)
        if(attendance== null){
            println("No valid check-in yet")
            return null     //Invalid check-out
        }

        val formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

        val totHrs= Duration.between(attendance.checkInDateTime,parsedDateTime)
        attendance.checkOutDateTime=parsedDateTime
        attendance.workingHrs=totHrs
        return "EmpId: $empId CheckInTime: ${attendance.checkInDateTime.format(formatter)} " +
                "CheckOutTime: ${checkOutDateTime.format(formatter)} workingHrs: ${totHrs.toHours()}h ${totHrs.toMinutesPart()}m"
    }

    fun validateCheckOut(empId: String, checkOutDateTime: LocalDateTime): Attendance? {
        val attendance: Attendance? =checkInList.find {
            it.id == empId &&
                    it.checkInDateTime.toLocalDate() == checkOutDateTime.toLocalDate() &&
                    it.checkOutDateTime == null
        }
        // Check whether check in time is greater than or equal to check out time in terms of day,hr,minutes ignoring seconds
        if(attendance==null ||attendance.checkInDateTime.truncatedTo(ChronoUnit.MINUTES) >= checkOutDateTime.truncatedTo(ChronoUnit.MINUTES)) {
            return null
        }
        return attendance
    }

    fun getAttendanceList(): AttendanceList{
       return checkInList

    }

    fun getIncompleteAttendances(): List<Attendance> {
        return checkInList.filter { it.checkOutDateTime == null }
    }

    fun getTotalWorkingHrsBetween(startingDate: String, endingDate: String): Map<String, Duration>? {

        val startDate = parseDate(startingDate)
        if(startDate==null){
            println("Invalid starting date")
            return null
        }

        val endDate = parseDate(endingDate)
        if(endDate==null){
            println("Invalid ending date")
            return null
        }

        //Filter attendances between the given dates
        val filteredList = checkInList.filter {
            it.checkOutDateTime != null &&
                    it.checkInDateTime.toLocalDate() in startDate..endDate
        }

        //Create a map to store total duration per employee
        val map = mutableMapOf<String, Duration>()

        //Accumulate working hours per employee
        for (attendance in filteredList) {
            val empId = attendance.id
            val duration = attendance.workingHrs
            map[empId] = map.getOrDefault(empId, Duration.ZERO) + duration
        }
        return map
    }

    fun parseDate(input: String): LocalDate? {
        val formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return try {
            LocalDate.parse(input, formatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }


    fun getEmployee(empId: String): Employee? {   //Return Employee object
        return employeeList.find { it.id == empId }
    }
}
