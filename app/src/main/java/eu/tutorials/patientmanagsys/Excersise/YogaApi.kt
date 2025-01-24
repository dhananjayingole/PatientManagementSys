package eu.tutorials.patientmanagsys.Excersise

import eu.tutorials.patientmanagsys.Excersise.yogamodel.Pose
import eu.tutorials.patientmanagsys.Excersise.yogamodel.Yoga
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YogaApi {
    @GET("categories")
    suspend fun getYoga(): Response<Yoga>

    // Add endpoint for single pose if needed
    @GET("poses/{id}")
    suspend fun getPoseById(@Path("id") id: Int): Response<Pose>
}