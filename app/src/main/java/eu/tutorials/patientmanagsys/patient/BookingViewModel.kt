package eu.tutorials.patientmanagsys.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import eu.tutorials.patientmanagsys.model.Booking
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class BookingViewModel : ViewModel() {
    private val db = Firebase.database.getReference("Booking")

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun saveBooking(name: String, age: String, doctor: String, date: String, time: String) {
        val bookingId = db.push().key ?: return
        val booking = Booking(name, age, doctor, date, time)

        db.child(bookingId).setValue(booking)
            .addOnSuccessListener {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavigateToBookingScreen)
                }
            }

            .addOnFailureListener { e ->
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowToast(e.message ?: "Error saving booking"))
                }
            }
    }

    sealed class UiEvent {
        object NavigateToBookingScreen : UiEvent()
        data class ShowToast(val message: String) : UiEvent()
    }
}
