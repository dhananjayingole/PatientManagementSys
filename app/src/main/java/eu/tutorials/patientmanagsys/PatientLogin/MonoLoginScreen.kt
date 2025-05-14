package eu.tutorials.patientmanagsys.PatientLogin

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

@Composable
fun SignupScreen(
    onSignupComplete: () -> Unit,
    viewModel: SignupViewModel = viewModel(
        factory = SignupViewModelFactory(LocalContext.current as Activity)
    )
) {
    // Observe signup success to navigate
    if (viewModel.isSignupSuccessful) {
        LaunchedEffect(Unit) {
            onSignupComplete()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (viewModel.isOtpSent) "Verify OTP" else "Sign Up",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        if (!viewModel.isOtpSent) {
            MobileNumberSection(viewModel)
        } else {
            OtpSection(viewModel)
        }

        // Error message
        viewModel.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
            LaunchedEffect(error) {
                delay(3000)
                viewModel.resetError()
            }
        }
    }
}

@Composable
private fun MobileNumberSection(viewModel: SignupViewModel) {
    OutlinedTextField(
        value = viewModel.mobileNumber,
        onValueChange = viewModel::updateMobileNumber,
        label = { Text("Mobile Number") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Enter 10-digit mobile number") },
        isError = viewModel.errorMessage != null
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = viewModel::sendOtp,
        modifier = Modifier.fillMaxWidth(),
        enabled = !viewModel.isLoading && viewModel.mobileNumber.length == 10
    ) {
        if (viewModel.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text("Send OTP")
        }
    }
}

@Composable
private fun OtpSection(viewModel: SignupViewModel) {
    Text(
        text = "OTP sent to +91 ${viewModel.mobileNumber}",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 16.dp)
    )

    OutlinedTextField(
        value = viewModel.otp,
        onValueChange = viewModel::updateOtp,
        label = { Text("OTP") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Enter 6-digit OTP") },
        isError = viewModel.errorMessage != null
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = viewModel::verifyOtp,
        navController.navigate(Routes.PatientListScreen)
        modifier = Modifier.fillMaxWidth(),
        enabled = !viewModel.isLoading && viewModel.otp.length == 6
    ) {
        if (viewModel.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text("Verify & Sign Up")
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    TextButton(
        onClick = { if (viewModel.countdown == 0) viewModel.resendOtp() },
        enabled = viewModel.countdown == 0 && !viewModel.isLoading
    ) {
        if (viewModel.countdown > 0) {
            Text("Resend OTP in ${viewModel.countdown}s")
        } else {
            Text("Resend OTP")
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    TextButton(
        onClick = { viewModel.isOtpSent = false }
    ) {
        Text("Change Mobile Number")
    }
}
