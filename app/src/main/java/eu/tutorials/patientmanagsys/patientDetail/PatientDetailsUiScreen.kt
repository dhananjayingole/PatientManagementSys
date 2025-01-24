package eu.tutorials.patientmanagsys.patientDetail

data class PatientDetailsUiScreen(
    val name:String = "",
    val age:String = "",
    val gender:Int = 0,
    val doctorAssigned:String = "",
    val prescription:String = ""
)
