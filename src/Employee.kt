class Employee(
    var firstName: String,
    var lastName: String,
    var role: Role?,
    var reportingTo: Manager?
){
    var id: String=""
    companion object{
        var serialNumber=1010
    }

    fun validate(): Boolean{
        var isValid=true
        if(!isValidName(firstName) || !isValidName(lastName)){
            isValid=false
        }

        //Check whether the employee role is correct
        if (role == null) {
            isValid=false
        }

        //Validate the reports to
        if(reportingTo==null){
            isValid=false
        }
        return isValid
    }

    fun generateEmpId(){
        id= "PieQ$serialNumber"
        serialNumber++
    }

    fun getErrorMessage(): String{
        if(!isValidName(firstName) || !isValidName(lastName)){
            return "Employee firstName or lastName can't be empty or number"
        }

        //Check whether the employee role is correct
        if (role == null) {
            return "Invalid role. Valid roles are: $Role"
        }

        //Validate the reports to
        if(reportingTo==null){
            return "Invalid reportingTo. Valid managers are $Manager"
        }

        return "No Error"
    }

    fun isValidName(name: String): Boolean{
        return (!name.isBlank() && name.all { it.isLetter() })
    }

    fun update(firstName: String,lastName: String,role: String,reportingTo: String): Boolean{
        if(!isValidName(firstName) || !isValidName(lastName)){
            println("Employee firstName or lastName can't be empty or number")
            return false
        }

        //Check whether the employee role is correct
        if (Role.from(role) == null) {
            println("Invalid role. Valid roles are: $Role")
            return false
        }

        //Validate the reports to
        if(Manager.from(reportingTo)==null){
            println("Invalid reportingTo. Valid managers are $Manager")
            return false
        }
        this.firstName=firstName
        this.lastName=lastName
        this.role=Role.from(role)
        this.reportingTo=Manager.from(reportingTo)
        return true
    }

    override fun toString(): String {
        return buildString {
            appendLine("empID        : $id")
            appendLine("Name         : $firstName $lastName")
            appendLine("Role         : $role")
            appendLine("Reporting To : $reportingTo")
        }
    }
}