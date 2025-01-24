package eu.tutorials.patientmanagsys.model


data class Patient(
    val id: String = "",
    val name: String = "",
    val age: String = "",
    val gender: Int = 0,                // 1 for male, 2 for female
    val doctorAssigned: String = "",
    val prescription: String = ""
)
