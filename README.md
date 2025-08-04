# PieQ Attendance Management

## Class Employee
&nbsp;Contains employee data fields such as id, firstName, lastName, role, reportingTo. 
## Methods
### Validate(): Boolean
&nbsp;Validate the employee details such as check whether an employee name is valid or empty and return true/false based on the validation.
### generateId()
&nbsp;Generate employee id and assign it to the employee id field.
### getErrorMessage(): String
&nbsp;Validate employee fields and return error message in string format such as "Invalid name" error.
### update(): Boolean
&nbsp;Validate the new values, update the employee data fields and return true if the updation is successful, otherwise return false.
### toString(): String
&nbsp;Format the employee data fields and return the formatted String.

## Class Attendance
&nbsp;Contains employee data fields such as empId, checkInTime, checkOutTime, workingHours.
## Methods
### toString(): String
&nbsp;Format the fields and return the formatted string

## Class EmployeeList
&nbsp;Inherits the ArrayList class, set the object type as Employee and ovverride the add method of arrayList.
## Methods
### add(): Boolean
&nbsp;Take employee object as input, add it to the list if its is valid and return true if the object is successfully added otherwise return false.

## Class AttendanceList
&nbsp;Inherits the ArrayList class, set the object type as Attendance and ovverride the add method of arrayList
## Methods
### add(): Boolean
&nbsp;Take attendance object as input, add it to the list if its is valid and return true if the object is successfully added otherwise return false.

## Class EmployeeManager
&nbsp;Class that interact with the main function. Contains objects for EmployeeList and AttendanceList.
## Methods
### addEmployee(): String
&nbsp;Get the employee object and add it to the list if the object is valid. Also return success or error message.
### deleteEmployee(): Boolean
&nbsp;Get the empId and remove the employee object from the list if the employee id exist.
### updateEmployee(): String
&nbsp;Update the employee data fields with new values if the new values are valid. Return success or error message as string.
### getEmployeeList(): List
&nbsp;Return the employee list
### chckIn(): Boolean
&nbsp;Add check-in entry to the attendance list if it is a valid check-in.
### hasAlreadyCheckedIn(): Boolean
&nbsp;Checks whether an employee already checked in that particular day or not.
### checkOut(): Boolean
&nbsp;Update the check out time and working hours data fields if it is a valid check out.
### isValidCheckOut(): Attendance
&nbsp;Validate the check out conditions and returns attendance object if the check out is valid
### getIncompleteAttendance(): List
&nbsp;Return the list of attendance where checked in but no checked out.
### getTotalWorkingHrsBetween(): Map
&nbsp;Compute the total working hours of each employees between two dates.

## main()
&nbsp;Display menu list, get inputs from the user, call the functions based on the user choice and display the output.

