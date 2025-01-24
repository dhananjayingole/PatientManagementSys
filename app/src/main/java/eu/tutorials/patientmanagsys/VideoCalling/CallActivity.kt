package eu.tutorials.patientmanagsys.VideoCalling


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallFragment
import eu.tutorials.patientmanagsys.R

class CallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        // Add the Zego call fragment
        addCallFragment()
    }

    private fun addCallFragment() {
        val appID: Long = 549060519 // Replace with your App ID
        val appSign = "7156c8576ef8f5612d6377d32c7a630c4fa0c9b01f83cba9c1cb160dda290ef8" // Replace with your App Sign
        val callID: String = intent?.getStringExtra("CALL_ID") ?: "default_call_id"
        // Call ID from intent
        val userID  = "user_${System.currentTimeMillis()}" // Generate a unique User ID
        val userName = "User_${System.currentTimeMillis()}" // Generate a User Name

        // Configure the call for one-on-one video calls
        val config = ZegoUIKitPrebuiltCallConfig.oneOnOneVideoCall()

        // Create the call fragment
        val fragment = ZegoUIKitPrebuiltCallFragment.newInstance(
            appID, appSign, userID, userName, callID, config
        )

        // Add or replace the fragment in the container
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
        }
    }
}
