package eu.tutorials.patientmanagsys.PatientLogin

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class SignupViewModel(private val activity: Activity) : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    // UI states
    var mobileNumber by mutableStateOf("")
        private set
    var otp by mutableStateOf("")
        private set
    var isOtpSent by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var countdown by mutableStateOf(0)
        private set
    var isSignupSuccessful by mutableStateOf(false)
        private set

    fun updateMobileNumber(number: String) {
        mobileNumber = number.filter { it.isDigit() }.take(10)
    }

    fun updateOtp(newOtp: String) {
        otp = newOtp.filter { it.isDigit() }.take(6)
    }

    fun sendOtp() {
        if (mobileNumber.length != 10) {
            errorMessage = "Please enter a valid 10-digit mobile number"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91$mobileNumber") // India country code
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        // Auto-verification (SMS not needed)
                        isLoading = false
                        signInWithPhoneAuthCredential(credential)
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        isLoading = false
                        errorMessage = when (e) {
                            is FirebaseAuthInvalidCredentialsException -> "Invalid phone number"
                            is FirebaseTooManyRequestsException -> "Quota exceeded. Try again later."
                            else -> "Verification failed: ${e.message}"
                        }
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        storedVerificationId = verificationId
                        resendToken = token
                        isLoading = false
                        isOtpSent = true
                        startCountdown()
                    }
                })
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    fun verifyOtp() {
        if (otp.length != 6) {
            errorMessage = "Please enter a valid 6-digit OTP"
            return
        }

        storedVerificationId?.let { verificationId ->
            val credential = PhoneAuthProvider.getCredential(verificationId, otp)
            signInWithPhoneAuthCredential(credential)
        } ?: run {
            errorMessage = "Verification failed. Please try again."
        }
    }

    fun resendOtp() {
        if (countdown > 0) return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91$mobileNumber")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        isLoading = false
                        signInWithPhoneAuthCredential(credential)
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        isLoading = false
                        errorMessage = "Failed to resend OTP: ${e.message}"
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        storedVerificationId = verificationId
                        resendToken = token
                        isLoading = false
                        startCountdown()
                    }
                })
                .setForceResendingToken(resendToken!!)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential).await()
                isSignupSuccessful = true
            } catch (e: Exception) {
                errorMessage = when (e) {
                    is FirebaseAuthInvalidCredentialsException -> "Invalid OTP"
                    else -> "Authentication failed: ${e.message}"
                }
            } finally {
                isLoading = false
            }
        }
    }

    private fun startCountdown() {
        countdown = 60
        viewModelScope.launch {
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
        }
    }

    fun resetError() {
        errorMessage = null
    }
}