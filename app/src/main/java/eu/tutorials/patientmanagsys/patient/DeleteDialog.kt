package eu.tutorials.patientmanagsys.patient

import android.app.AlertDialog
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    title: String,
    message: String,
    onDialogDismiss: () -> Unit,
    onConfirmButtonClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDialogDismiss,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onConfirmButtonClicked) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDialogDismiss) {
                Text("Cancel")
            }
        }
    )
}
