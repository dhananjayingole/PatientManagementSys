package eu.tutorials.patientmanagsys.patient


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.People
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import eu.tutorials.patientmanagsys.R
import eu.tutorials.patientmanagsys.navigation.Routes

val items = listOf(
    eu.tutorials.patientmanagsys.model.BottomNavigation(
        title = "Patients",
        icon = Icons.Rounded.People
    ),
    eu.tutorials.patientmanagsys.model.BottomNavigation(
        title = "AboutUs",
        icon = Icons.Rounded.Home
    ),
    eu.tutorials.patientmanagsys.model.BottomNavigation(
        title = "Notifications",
        icon = Icons.Rounded.Notifications
    ),
    eu.tutorials.patientmanagsys.model.BottomNavigation(
        title = "Admin",
        icon = Icons.Rounded.AccountCircle
    ),
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val selectedIndex = remember { mutableStateOf(0) }
    NavigationBar {
        Row(
            modifier = Modifier.background(colorResource(id = R.color.app_bar_color))
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedIndex.value == index,
                    onClick = {
                        selectedIndex.value = index
                        when (item.title) {
                            "Patients" ->
                                navController.navigate(Routes.PatientListScreen)
                            "AboutUs" ->
                                navController.navigate(Routes.HomeScreen)
                            "Notifications" ->
                                navController.navigate(Routes.NotificationScreen)
                            "Admin" ->
                                navController.navigate(Routes.AdminMessageScreen)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}


