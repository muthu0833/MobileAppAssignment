package com.inila.mobileappassignment.data.remote

import com.inila.mobileappassignment.data.model.Course

interface CourseRemoteDataSource {
    suspend fun getCourses(): List<Course>
}

class FakeCourseRemoteDataSource : CourseRemoteDataSource {
    override suspend fun getCourses(): List<Course> {
        return listOf(
            Course("1", "Intro to Android", "Google", 0, 2),
            Course("2", "Advanced Compose", "JetBrains", 0, 2),
            Course("3", "Generative AI", "John Smith", 0, 2),
            Course("4", "Full Stack Development", "David Brown", 0, 2)
        )
    }
}

