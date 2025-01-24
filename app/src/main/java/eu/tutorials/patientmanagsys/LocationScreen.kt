package eu.tutorials.patientmanagsys

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay
import java.io.IOException
import java.util.Date
import java.util.Locale

@SuppressLint("MissingPermission", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LocationScreen(fusedLocationClient: FusedLocationProviderClient, onBackClick: () -> Unit) {
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var locationServiceDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                if (isLocationEnabled()) {
                    getLocation(fusedLocationClient, context) { lat, lon, addr ->
                        latitude = lat.toString()
                        longitude = lon.toString()
                        address = addr
                        showDialog = true
                    }
                } else {
                    locationServiceDialog = true
                }
            } else {
                Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    if (locationServiceDialog) {
        AlertDialog(
            shape = RoundedCornerShape(20.dp),
            onDismissRequest = { locationServiceDialog = false },
            title = { Text("Enable Location Services") },
            text = { Text("Please enable location services for this feature to work.") },
            confirmButton = {
                Button(onClick = {
                    locationServiceDialog = false
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }) {
                    Text("Turn On")
                }
            },
            dismissButton = {
                Button(onClick = { locationServiceDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    Scaffold(
        topBar = {
            TopBar(title = "Location Screen", onBackClick)
        },
        content = { paddingValues ->
            // Add a scrollable state to the column
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White)
                    .verticalScroll(scrollState),  // Enable scrolling
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.emergency),
                    contentDescription = "Main Icon",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = rememberImagePainter("https://cdn-icons-png.flaticon.com/512/684/684908.png"),
                    contentDescription = "Location Icon",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Tap the button to share your location for emergency medical help",
                    color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.app_bar_color))
                ) {
                    Text("Get Location", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = latitude,
                    onValueChange = {},
                    label = { Text("Latitude") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = longitude,
                    onValueChange = {},
                    label = { Text("Longitude") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = {},
                    label = { Text("Address") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
    )

    if (showDialog) {
        AlertDialog(
            shape = RoundedCornerShape(30.dp),
            onDismissRequest = { showDialog = false },
            title = { Text("Emergency Alert", color = Color.Black) },
            text = {
                Text(
                    "Emergency medical assistance will be provided at your given location soon.",
                    color = Color.Green
                )
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )

        LaunchedEffect(Unit) {
            delay(3000)
            showDialog = false
        }
    }
}

@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title, style = MaterialTheme.typography.h5)
        },
        backgroundColor = colorResource(id = R.color.app_bar_color),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@SuppressLint("MissingPermission")
fun getLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    onLocationFetched: (Double, Double, String) -> Unit
) {
    fusedLocationClient.getCurrentLocation(
        com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY, null
    ).addOnSuccessListener { location ->
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            val address = getAddressFromLatLng(context, latitude, longitude)

            onLocationFetched(latitude, longitude, address)
            saveLocationToFirebase(latitude, longitude, address)
        } else {
            Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show()
        }
    }
}

fun getAddressFromLatLng(context: Context, latitude: Double, longitude: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
        if (!addresses.isNullOrEmpty()) {
            addresses[0].getAddressLine(0) ?: "Address not found"
        } else {
            "Address not found"
        }
    } catch (e: IOException) {
        e.printStackTrace()
        "Error fetching address"
    }
}

fun saveLocationToFirebase(latitude: Double, longitude: Double, address: String) {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("Locations")
    val locationId = ref.push().key ?: Date().time.toString()
    val locationData = mapOf(
        "latitude" to latitude,
        "longitude" to longitude,
        "address" to address,
        "timestamp" to System.currentTimeMillis()
    )
    ref.child(locationId).setValue(locationData)
}
