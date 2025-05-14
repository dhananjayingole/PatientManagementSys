package eu.tutorials.patientmanagsys.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import eu.tutorials.patientmanagsys.BottomScreen.AdminMessageScreen
import eu.tutorials.patientmanagsys.BottomScreen.AdminMessageViewModel
import eu.tutorials.patientmanagsys.BottomScreen.NotificationScreen
import eu.tutorials.patientmanagsys.BottomScreen.NotificationViewModel
import eu.tutorials.patientmanagsys.Excersise.YogaDetailScreen
import eu.tutorials.patientmanagsys.Excersise.YogaScreen
import eu.tutorials.patientmanagsys.Excersise.YogaViewModel
import eu.tutorials.patientmanagsys.HomeScreen
import eu.tutorials.patientmanagsys.LocationScreen
import eu.tutorials.patientmanagsys.LoginScreens
import eu.tutorials.patientmanagsys.SignUpScreen
import eu.tutorials.patientmanagsys.patient.BookingScreen
import eu.tutorials.patientmanagsys.patient.BookingViewModel
import eu.tutorials.patientmanagsys.patient.BottomNavigationBar
import eu.tutorials.patientmanagsys.patient.EmergencyScreen
import eu.tutorials.patientmanagsys.patient.PatientListScreen
import eu.tutorials.patientmanagsys.patient.PatientListViewModel
import eu.tutorials.patientmanagsys.patientDetail.PatientDetailScreen
import eu.tutorials.patientmanagsys.patientDetail.PatientDetailsViewModel

const val ADMIN_EMAIL = "dhananjayingole276@gmail.com"

@Composable
fun NavGraphSetup(navController: NavHostController, fusedLocationClient: FusedLocationProviderClient) {
    var showBottomBar by remember { mutableStateOf(true) }
    var showTopBar by remember { mutableStateOf(true) }
    var screenTitle by remember { mutableStateOf("Patient Management") } // Default screen name
    val currentUser = FirebaseAuth.getInstance().currentUser

    fun isAdminUser(): Boolean {
        return currentUser?.email == ADMIN_EMAIL
    }

    Scaffold(
        topBar = {
            if (showTopBar) {
                // Pass the dynamic screen title here
                eu.tutorials.patientmanagsys.TopBar(title = screenTitle, onBackClick = { navController.navigateUp() })
            }
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.LoginScreen,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.LoginScreen) {
                showBottomBar = false
                showTopBar = false
                screenTitle = "Login" // Set screen title for LoginScreen
                LoginScreens(navController)
            }
            composable(Routes.SignUpScreen) {
                showBottomBar = false
                showTopBar = false
                screenTitle = "Sign Up" // Set screen title for SignUpScreen
                SignUpScreen(navController)
            }
            composable(Routes.HomeScreen) {
                showBottomBar = true
                showTopBar = false
                screenTitle = "Home" // Set screen title for HomeScreen
                HomeScreen(navController)
            }
            composable(Routes.PatientListScreen) {
                showBottomBar = false
                showTopBar = false
                screenTitle = "Patient List" // Set screen title for PatientListScreen
                PatientListScreen(
                    onFabClick = { navController.navigate(Routes.PatientDetailScreen) },
                    viewModel = PatientListViewModel(),
                    navController
                )
            }
            composable(Routes.PatientDetailScreen) {
                showBottomBar = true
                showTopBar = false
                screenTitle = "Patient Details" // Set screen title for PatientDetailScreen
                PatientDetailScreen(
                    onBackClick = { navController.navigateUp() },
                    viewModel = PatientDetailsViewModel(),
                    navController = navController
                )
            }
            composable(Routes.NotificationScreen) {
                showBottomBar = true
                showTopBar = true
                screenTitle = "Notifications" // Set screen title for NotificationScreen
                NotificationScreen(navController, viewModel = NotificationViewModel())
            }
            composable(Routes.BookingScreen) {
                showBottomBar = true
                showTopBar = true
                screenTitle = "Book Appointment" // Set screen title for BookingScreen
                BookingScreen(navController, viewModel = BookingViewModel())
            }
            composable(Routes.LocationScreen) {
                showBottomBar = true
                showTopBar = false
                screenTitle = "Location" // Set screen title for LocationScreen
                LocationScreen(
                    fusedLocationClient = fusedLocationClient,
                    onBackClick = { navController.navigateUp() }
                )
            }
            composable(Routes.EmergencyScreen) {
                showBottomBar = true
                showTopBar = true
                screenTitle = "Emergency" // Set screen title for EmergencyScreen
                EmergencyScreen(
                    emergencyNumber = "123456789",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                )
            }
            composable(Routes.YogaScreen) {
                showBottomBar = true
                showTopBar = true
                screenTitle = "Yoga Exercises" // Set screen title for YogaScreen
                YogaScreen(navController)
            }
            composable(Routes.YogaDetailScreen,
                arguments = listOf(navArgument("poseId") { type = NavType.IntType })
            ) { backStackEntry ->
                val viewModel: YogaViewModel = viewModel()
                val poseId = backStackEntry.arguments?.getInt("poseId") ?: return@composable
                showBottomBar = true
                showTopBar = true
                screenTitle = "Yoga Details" // Set screen title for YogaDetailScreen
                YogaDetailScreen(navController = navController, poseId = poseId, viewModel = viewModel)
            }
            composable(Routes.AdminMessageScreen) {
                if (isAdminUser()) {
                    showBottomBar = true
                    showTopBar = true
                    screenTitle = "Admin Messages" // Set screen title for AdminMessageScreen
                    AdminMessageScreen(viewModel = AdminMessageViewModel(), navController)
                } else {
                    navController.navigate(Routes.HomeScreen) {
                        popUpTo(Routes.AdminMessageScreen) { inclusive = true }
                    }
                }
            }
        }
    }
}
