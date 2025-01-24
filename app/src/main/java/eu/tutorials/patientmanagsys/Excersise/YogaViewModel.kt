package eu.tutorials.patientmanagsys.Excersise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.patientmanagsys.Excersise.yogamodel.Pose
import eu.tutorials.patientmanagsys.Excersise.yogamodel.Yoga
import kotlinx.coroutines.launch

class YogaViewModel : ViewModel() {
    private val _yogaResult = MutableLiveData<NetworkResponse<Yoga>>()
    val yogaResult: LiveData<NetworkResponse<Yoga>> = _yogaResult

    private val _selectedPose = MutableLiveData<Pose>()
    val selectedPose: LiveData<Pose> = _selectedPose

    init {
        fetchYogaData()
    }

    fun fetchYogaData() {
        viewModelScope.launch {
            _yogaResult.value = NetworkResponse.Loading
            try {
                val response = RetrofitInstance.yogaApi.getYoga()
                if (response.isSuccessful) {
                    response.body()?.let { yoga ->
                        _yogaResult.value = NetworkResponse.Success(yoga)
                    } ?: run {
                        _yogaResult.value = NetworkResponse.Error("Response body is empty")
                    }
                } else {
                    _yogaResult.value = NetworkResponse.Error("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                _yogaResult.value = NetworkResponse.Error("Network error: ${e.localizedMessage}")
            }
        }
    }

    fun selectPose(pose: Pose) {
        _selectedPose.value = pose
    }
}
