import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Attendance(
    val id: String,
    val checkInDateTime: LocalDateTime,
){
    var checkOutDateTime: LocalDateTime? = null
    var workingHrs: Duration= Duration.ZERO
    override fun toString(): String {
        val formatter= DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm")
        return buildString {
            appendLine("empId            : $id")
            appendLine("CheckInDateTime  : ${checkInDateTime.format(formatter)}")
            appendLine("CheckOutDateTime : ${checkOutDateTime?.format(formatter)?:"N/A"}")
            appendLine("Working hours    : ${workingHrs.toHours()}h ${workingHrs.toMinutes()%60}m")
        }
    }
}