package eu.tutorials.patientmanagsys.patient

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.tutorials.patientmanagsys.model.Patient

@Composable
fun patientItem(
    patient: Patient,
    onItemClicked:() -> Unit,
    onDeleteConfirm:()-> Unit
    ) {

   var showDialog by remember { mutableStateOf(false) }

    if(showDialog){
        DeleteDialog(
            title ="Delete",
            message = "Are you sure ,you want to delete" +
            "patient \"${patient.name}\" from patient list",
            onDialogDismiss = { showDialog = false },
            onConfirmButtonClicked = {
                onDeleteConfirm()
                showDialog = false
            }
        )
    }
Card(
    modifier = Modifier.clickable { onItemClicked() }.padding(horizontal = 16.dp, vertical = 8.dp)
    ,elevation = 4.dp
    )
{
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(9f)) {
       Text(
           text = patient.name,
           style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
           maxLines = 1,
           overflow = TextOverflow.Ellipsis
       )
            Text(
                text = "Assigned to ${patient.doctorAssigned}",
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { showDialog = true }
        )
        {
      Icon(imageVector = Icons.Filled.Delete,contentDescription = "Delete")
        }
    }
}
}