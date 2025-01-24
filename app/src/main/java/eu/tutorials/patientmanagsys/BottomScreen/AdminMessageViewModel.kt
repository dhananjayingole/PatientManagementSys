package eu.tutorials.patientmanagsys.BottomScreen

import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdminMessageViewModel : ViewModel() {
    private val db = Firebase.database.getReference("notifications")

    fun postNotification(message: String) {
        val notificationId = db.push().key ?: return
        val notification = Notification(
            message = message,
            timestamp = System.currentTimeMillis()
        )
        db.child(notificationId).setValue(notification)
    }
}
