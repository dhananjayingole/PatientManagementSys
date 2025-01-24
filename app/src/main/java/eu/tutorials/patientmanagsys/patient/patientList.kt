package eu.tutorials.patientmanagsys.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import eu.tutorials.patientmanagsys.R
import eu.tutorials.patientmanagsys.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PatientListScreen(
    onFabClick: () -> Unit,
    viewModel: PatientListViewModel,
    navController: NavHostController
) {
    val patientList by viewModel.patientList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showBottomBar by remember { mutableStateOf(true) }

    // State for controlling bottom sheet visibility
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    // Filter the patient list based on the search query
    val filteredPatientList = if (searchQuery.isEmpty()) {
        patientList
    } else {
        patientList.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            MoreBottomSheet(
                modifier = Modifier.padding(16.dp),
                navController = navController,
                emergencyNumber = "7083163282",
            )
        },
        sheetState = sheetState
    ) {
        Scaffold(
            topBar = {
                ListAppBar(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { query -> searchQuery = query },
                    onMenuClick = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    } // Show bottom sheet on menu click
                )
            },
            floatingActionButton = { ListFab(onFabClick = onFabClick) },
            bottomBar = {
                if (showBottomBar) {
                    BottomNavigationBar(navController)
                }
            }
        ) { innerPadding ->
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if(filteredPatientList.isNotEmpty()) {
                LazyColumn(
                    contentPadding = innerPadding,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredPatientList) { patient ->
                        patientItem(
                            patient = patient,
                            onItemClicked = {
                                navController.navigate(Routes.PatientDetailScreen)
                            },
                            onDeleteConfirm = {
                                viewModel.deletePatient(patient.id)
                            }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Patients Found\nAdd Patients Details by pressing the add button.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ListAppBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onMenuClick: () -> Unit // Callback for the three-dot menu click
) {
    var showSearchField by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            if (showSearchField) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = { Text("Search...") },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = "Patient Tracker",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                showSearchField = !showSearchField
            }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        },
        actions = {
            IconButton(onClick = onMenuClick) { // Three-dot menu icon
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More Options")
            }
        },
        backgroundColor = colorResource(id = R.color.app_bar_color)
    )
}

@Composable
fun ListFab(
    onFabClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onFabClick
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add patient Button"
        )
    }
}

@Composable
fun MoreBottomSheet(
    modifier: Modifier,
    emergencyNumber: String,
    navController: NavHostController,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(350.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Emergency Call Button
            Row(modifier = modifier.padding(16.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.baseline_call_24),
                    contentDescription = "Emergency Call"
                )
                Text(
                    text = "Emergency Call",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.clickable {
                       navController.navigate(Routes.EmergencyScreen)
                    }
                )
            }

            Row(modifier = modifier.padding(16.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.baseline_yoga_24),
                    contentDescription = "Yoga & Excersise"
                )
                Text(
                    text = "Yoga & Excersise",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        navController.navigate(Routes.YogaScreen)
                    }
                )
            }
            // Share Location Option
            Row(modifier = modifier.padding(16.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.share_btn),
                    contentDescription = "Share"
                )
                Text(
                    text = "Ambulance service emergency.",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        // Location
                        navController.navigate(Routes.LocationScreen)
                    }
                )
            }
        }
    }
}
