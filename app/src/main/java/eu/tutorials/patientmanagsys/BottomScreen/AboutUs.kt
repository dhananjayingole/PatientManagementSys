package eu.tutorials.patientmanagsys.BottomScreen

import android.content.Intent
import android.net.Uri
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AboutUsContent() {

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "About Us",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White// Slightly transparent white
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Welcome to Apollo Hospital, where we provide top-notch medical care with a personal touch. Our team of dedicated professionals is committed to ensuring the best possible outcomes for our patients. We offer a wide range of services, from general medicine to specialized treatments, all in a state-of-the-art facility.",
                fontSize = 16.sp,
                color = Color.White, // Slightly transparent white
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Our mission is to deliver compassionate and comprehensive healthcare to our community. We strive to create a healing environment where patients feel valued and cared for.",
                fontSize = 16.sp,
                color = Color.White, // Slightly transparent white
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Our hospital is equipped with the latest medical technology and staffed by highly trained professionals. We offer a variety of specialized services, including cardiology, neurology, gynecology, and dermatology. Our state-of-the-art facilities ensure that our patients receive the best possible care in a comfortable and welcoming environment.",
                fontSize = 16.sp,
                color = Color.White, // Slightly transparent white
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "We believe in a patient-centered approach to healthcare, where the needs and preferences of our patients are always our top priority. Our dedicated team works tirelessly to provide personalized care and support to each and every patient, ensuring that they feel heard, respected, and cared for.",
                fontSize = 16.sp,
                color = Color.White, // Slightly transparent white
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Thank you for choosing Apollo Hospital . We are honored to be a part of your healthcare journey and look forward to serving you with excellence and compassion.",
                fontSize = 16.sp,
                color = Color.White, // Slightly transparent white
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            TextButton(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://forms.gle/QX8JrPeGn82YFKhr9")
                    )
                    context.startActivity(intent)
                }
            ) {
                Text(text = "Click me to give feedback",
                    color = Color.White, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}

