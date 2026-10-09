package com.inila.mobileappassignment.data.remote

import com.inila.mobileappassignment.data.model.Course
import retrofit2.http.GET

interface CourseApiService {
    @GET("courses")
    suspend fun getCourses(): List<Course>
}
