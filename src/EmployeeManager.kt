import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class EmployeeManager {
    val employeeList = EmployeeList() //To store employees
    private val checkInList = AttendanceList()  //To store attendance records

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
        //Check whether first name and lastname are valid or not
        if(employee.validate()){
            employee.generateEmpId()
            employeeList.add(employee)
            return "Employee Added SuccessFully! empId:${employee.id} empName:${employee.firstName} ${employee.lastName}"
        }
        return employee.getErrorMessage()
    }

    //Remove an employee
    fun deleteEmployee(empId: String): Boolean{
        //Check whether given employee id exists
        val employee=employeeExists(empId)
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

    fun checkIn(empId: String, checkInDateTime: LocalDateTime): Boolean {
        if (employeeExists(empId)==null){
            println("Employee ID not found")
            return false      //Employee not found with the given id
        }
        if (hasAlreadyCheckedIn(empId, checkInDateTime)) {
            println("Employee has already checked in")
            return false     //Check whether the employee has already checked in today or not
        }

        checkInList.add(Attendance(empId, checkInDateTime))
        return true
    }

    fun hasAlreadyCheckedIn(empId: String, inputDateTime: LocalDateTime): Boolean {
        return checkInList.any {         //check whether already checked in today or not
            it.id == empId && it.checkInDateTime.toLocalDate() == inputDateTime.toLocalDate()
        }
    }

    fun checkOut(empId: String,checkOutDateTime: LocalDateTime):String?{
        if (employeeExists(empId)==null){
            println("Employee ID not found")
            return null      //Employee not found with the given id
        }
        val attendance: Attendance?= validateCheckOut(empId,checkOutDateTime)
        if(attendance== null){
            println("No valid check-in yet")
            return null     //Invalid check-out
        }

        val formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

        val totHrs= Duration.between(attendance.checkInDateTime,checkOutDateTime)
        attendance.checkOutDateTime=checkOutDateTime
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

    fun getTotalWorkingHrsBetween(startDate: LocalDate, endDate: LocalDate): Map<String, Duration> {
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


    fun getEmployee(empId: String): Employee? {   //Return Employee object
        return employeeList.find { it.id == empId }
    }
}
