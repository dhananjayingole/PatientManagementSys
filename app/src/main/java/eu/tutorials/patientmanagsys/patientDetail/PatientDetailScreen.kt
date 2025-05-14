package eu.tutorials.patientmanagsys.patientDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.tutorials.patientmanagsys.R

@Composable
fun PatientDetailScreen(
    viewModel: PatientDetailsViewModel,
    onBackClick:() ->Unit,
    navController: NavHostController
) {

    val state = viewModel.state
    val focusRequester = remember{FocusRequester()}

    Scaffold(
        topBar = {
            TopBar(onBackClick)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = state.name ,
                onValueChange ={ newValue->
                    viewModel.onAction(PatientDetailsEvents.EnteredName(newValue))
                },
                label = { Text(text = "Name") },
                textStyle = MaterialTheme.typography.body1,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value =state.age ,
                    onValueChange ={newValue->
                        viewModel.onAction(PatientDetailsEvents.EnteredAge(newValue))
                    },
                    label = { Text(text = "Age")},
                    textStyle = MaterialTheme.typography.body1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                RadioGroup(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Male",
                    selected = state.gender ==1,
                    onClick = {
                        viewModel.onAction(PatientDetailsEvents.SelectedMale)
                    }
                )
                RadioGroup(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Female",
                    selected = state.gender==2,
                    onClick = {
                        viewModel.onAction(PatientDetailsEvents.SelectedFemale)
                    }
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value =state.doctorAssigned ,
                onValueChange ={newValue->
                    viewModel.onAction(PatientDetailsEvents.EnteredAssignedDoctor(newValue))
                },
                label = { Text(text = "Assigned Doctor's Name")},
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.width(10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value =state.prescription ,
                onValueChange ={newValue->
                    viewModel.onAction(PatientDetailsEvents.EnteredPrescription(newValue))
                },
                label = { Text(text = "Prescription")},
                textStyle = MaterialTheme.typography.body1,
                singleLine = true
            )
            Spacer(modifier = Modifier.width(10.dp))
            val context = LocalContext.current
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onAction(PatientDetailsEvents.SaveButton)
                    navController.popBackStack()
                }
            ) {
                Text(text = "Save",
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun RadioGroup(
    modifier:Modifier = Modifier,
    text:String,
    selected:Boolean,
    onClick:()->Unit
){
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary
            )
        )
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun TopBar(
    onBackClick:()->Unit
){
    TopAppBar(
        title = {
            Text(text = "patients Details Screen",
                style = MaterialTheme.typography.h5
            )
        }, backgroundColor = colorResource(id = R.color.app_bar_color),
        navigationIcon = {
            IconButton(onClick = onBackClick
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back")
            }
        }
    )
}