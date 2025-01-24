package eu.tutorials.patientmanagsys.patientDetail

sealed class PatientDetailsEvents{
    data class EnteredName(val name:String):PatientDetailsEvents()
    data class EnteredAge(val age:String): PatientDetailsEvents()
    data class EnteredAssignedDoctor(val doctor:String):PatientDetailsEvents()
    data class EnteredPrescription(val prescription: String):PatientDetailsEvents()
    object SelectedMale:PatientDetailsEvents()
    object SelectedFemale:PatientDetailsEvents()
    object SaveButton:PatientDetailsEvents()
}