package eu.tutorials.patientmanagsys.patient

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import eu.tutorials.patientmanagsys.model.Booking
import eu.tutorials.patientmanagsys.navigation.Routes
import okhttp3.Route
import java.util.Calendar

@Composable
fun BookingScreen(navController: NavController, viewModel: BookingViewModel) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var doctor by remember { mutableStateOf("") }

    var date by remember { mutableStateOf("") }
    var time by remember{ mutableStateOf("") }

    val context = LocalContext.current
    val calender = Calendar.getInstance()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = doctor,
            onValueChange = { doctor = it },
            label = { Text("Doctor Assigned") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // date picker
        OutlinedTextField(
            value = date ,
            onValueChange = {},
            label = {Text("Booking date")},
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    DatePickerDialog(
                        context,
                        { _ , year,month,dayofMonth ->
                            date = "$dayofMonth/${month + 1}/$year"
                        },
                        calender.get(Calendar.YEAR),
                        calender.get(Calendar.MONTH),
                        calender.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
            readOnly = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = time,
            onValueChange = {},
            label = { Text("Booking Time") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            time = String.format("%02d:%02d", hourOfDay, minute)
                        },
                        calender.get(Calendar.HOUR_OF_DAY),
                        calender.get(Calendar.MINUTE),
                        true // Set to false for 12 hour format
                    ).show()
                },
            readOnly = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.saveBooking(name, age, doctor, date, time)
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Register")
        }
    }
}