package eu.tutorials.patientmanagsys.patient

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.*
import androidx.core.view.*
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.*
import eu.tutorials.patientmanagsys.R

@Composable
fun EmergencyScreen(emergencyNumber: String, videoUrl: String) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Click the call button for emergency doctor service",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        IconButton(
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$emergencyNumber")
                }
                context.startActivity(intent)
            },
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_call_24),
                contentDescription = "Emergency Call",
                tint = Color.Red,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Emergency Call",
            fontSize = 18.sp,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        VideoPlayer(videoUrl = videoUrl)
    }
}


@Composable
fun VideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    val exoPlayer = remember {
         SimpleExoPlayer.Builder(context).build().apply {
             val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
             setMediaItem(mediaItem)
             prepare()
             playWhenReady = true
         }
    }
    DisposableEffect(
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}