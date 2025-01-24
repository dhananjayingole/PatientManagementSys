package eu.tutorials.patientmanagsys.patientDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import eu.tutorials.patientmanagsys.model.Patient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PatientDetailsViewModel:ViewModel(){
    var state by mutableStateOf(PatientDetailsUiScreen())

    private val _eventFlow = MutableSharedFlow<UiEvent>()

    fun onAction(event: PatientDetailsEvents) {
        when(event) {
            is PatientDetailsEvents.EnteredName -> {
                state = state.copy(name = event.name)
            }
            is PatientDetailsEvents.EnteredAge -> {
                state = state.copy(age = event.age)
            }
            is PatientDetailsEvents.EnteredAssignedDoctor -> {
                state = state.copy(doctorAssigned = event.doctor)
            }
            is PatientDetailsEvents.EnteredPrescription -> {
                state = state.copy(prescription = event.prescription)
            }
            PatientDetailsEvents.SelectedFemale -> {
                state = state.copy(gender = 2)
            }
            PatientDetailsEvents.SelectedMale -> {
                state = state.copy(gender = 1)
            }
            PatientDetailsEvents.SaveButton -> {
                viewModelScope.launch {
                    try {
                        savePatient()
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowToast(e.message ?: "Couldn't save patient's details."))
                    }
                }
            }
        }
    }

    // Updated savePatient function in PatientDetailsViewModel
    private fun savePatient() {
        val age = state.age.toIntOrNull()
        when {
            state.name.isEmpty() -> throw TextFieldException("Please enter name.")
            state.age.isEmpty() -> throw TextFieldException("Please enter age.")
            state.gender == 0 -> throw TextFieldException("Please select gender")
            state.doctorAssigned.isEmpty() -> throw TextFieldException("Please enter doctor assigned.")
            state.prescription.isEmpty() -> throw TextFieldException("Please enter a prescription.")
            age == null -> throw TextFieldException("Age must be a number")
        }

        val database = Firebase.database
        val patientsRef = database.getReference("patients")

        // Generate a unique ID for the new patient
        val patientId = patientsRef.push().key ?: throw Exception("Failed to generate patient ID.")

        val newPatient = Patient(
            id = patientId,                      // Set unique ID for the patient
            name = state.name,
            age = state.age,
            gender = state.gender,
            doctorAssigned = state.doctorAssigned,
            prescription = state.prescription
        )

        // Save patient data to Firebase
        patientsRef.child(patientId).setValue(newPatient)
            .addOnSuccessListener {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.SaveNote)
                }
            }
            .addOnFailureListener { e ->
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowToast(e.message ?: "Couldn't save patient's details."))
                }
            }
    }


    sealed class UiEvent {
        data class ShowToast(val message: String): UiEvent()
        object SaveNote : UiEvent()
    }
}
class TextFieldException(message: String?): Exception(message)
