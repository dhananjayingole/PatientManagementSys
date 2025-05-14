package eu.tutorials.patientmanagsys

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import eu.tutorials.patientmanagsys.navigation.NavGraphSetup
import eu.tutorials.patientmanagsys.ui.theme.PatientManagSysTheme

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            PatientManagSysTheme {
                    val navController = rememberNavController()
                    // A surface container using the 'background' color from the theme.
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                       NavGraphSetup(navController,fusedLocationClient)
//                        SignupScreen(
//                            onSignupComplete = {
//                                // Navigate to next screen after successful signup
//                                finish()
//                            }
//                        )
                    }
                }
            }
        }
    }