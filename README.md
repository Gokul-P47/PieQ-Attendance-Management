# PieQ Attendance Management
---
## Class Employee
&nbsp;Represents an employee with data fields such as id, firstName, lastName, role, reportingTo. 
## Methods
### Validate(): Boolean
&nbsp;Validates the employee details (e.g., checks if name is empty or invalid). Returns true if valid, else false.
### generateId()
&nbsp;Generates a unique employee ID and assigns it to the id field.
### getErrorMessage(): String
&nbsp;Returns a string describing validation errors, e.g., "Invalid name".
### update(): Boolean
&nbsp;Updates employee fields after validating the new values. Returns true if successful, else false.
### toString(): String
&nbsp;Returns a formatted string representation of the employee's data.

---
## Class Attendance
&nbsp;Represents an employee's attendance details such as empId, checkInTime, checkOutTime, workingHours.
## Methods
### toString(): String
&nbsp;Returns a formatted string representation of the attendance record.

---
## Class EmployeeList
&nbsp;Extends ArrayList<Employee> and overrides the add() method.
## Methods
### add(): Boolean
&nbsp;Adds the employee object to the list if valid. Returns true on success, else false.

---
## Class AttendanceList
&nbsp;Extends ArrayList<Attendance> and overrides the add() method.
## Methods
### add(): Boolean
&nbsp;Adds the attendance object to the list if valid. Returns true on success, else false.

---
## Class EmployeeManager
&nbsp;Class that interact with the main function. Contains objects for EmployeeList and AttendanceList.
## Methods
### addEmployee(): String
&nbsp;Adds a new employee to the list after validation. Returns a success or error message.
### deleteEmployee(): Boolean
&nbsp;Removes an employee from the list by ID. Returns true if successful.
### updateEmployee(): String
&nbsp;Updates employee data after validation. Returns a success or error message.
### getEmployeeList(): List
&nbspReturns the list of all employees.
### checkIn(): Boolean
&nbsp;Adds a check-in entry for the employee if not already checked in that day.
### hasAlreadyCheckedIn(): Boolean
&nbsp;Checks if the employee has already checked in on the specified day.
### checkOut(): Boolean
&nbsp;Adds check-out time and calculates working hours if the check-out is valid.
### validateCheckOut(): Attendance
&nbsp;Validates if check-out is allowed and returns the corresponding Attendance object.
### getIncompleteAttendance(): List
&nbsp;Returns attendance records with check-in but no check-out.
### getTotalWorkingHrsBetween(): Map
&nbsp;Computes total working hours for each employee between two dates.

---
## main()
&nbsp;Displays a menu, takes user input, performs operations using the manager methods, and handles error messages.

---
