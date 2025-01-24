package eu.tutorials.patientmanagsys.patient

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.patientmanagsys.model.Patient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PatientListViewModel : ViewModel() {
    private val db = FirebaseDatabase.getInstance().getReference("patients")

    private val _patientList = MutableStateFlow<List<Patient>>(emptyList())
    val patientList = _patientList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)   // Use MutableStateFlow instead of mutableStateOf
    val isLoading = _isLoading.asStateFlow()           // Public access as StateFlow


    init {
        fetchPatients()
    }

    // Fetch all patients from Firebase
    fun fetchPatients() {
        _isLoading.value = true
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val patients = mutableListOf<Patient>()
                for (patientSnapshot in snapshot.children) {
                    val patient = patientSnapshot.getValue(Patient::class.java)
                    patient?.let { patients.add(it) }
                }
                _patientList.value = patients
                _isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                _isLoading.value = false
                // Handle error
            }
        })
    }

    fun deletePatient(patientId: String) {
        db.child(patientId).removeValue()
    }
}

