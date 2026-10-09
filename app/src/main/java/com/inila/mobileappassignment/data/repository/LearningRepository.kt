package com.inila.mobileappassignment.data.repository

import com.inila.mobileappassignment.data.local.AppDatabase
import com.inila.mobileappassignment.data.local.CourseDao
import com.inila.mobileappassignment.data.local.LessonDao
import com.inila.mobileappassignment.data.model.Course
import com.inila.mobileappassignment.data.model.Lesson
import com.inila.mobileappassignment.data.remote.CourseRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningRepository @Inject constructor(
    private val database: AppDatabase,
    private val courseDao: CourseDao,
    private val lessonDao: LessonDao,
    private val remoteDataSource: CourseRemoteDataSource
) {
    val allCourses: Flow<List<Course>> = courseDao.getAllCourses()
        .onStart {
            syncData()
        }

    private suspend fun syncData() {
        val existingCourses = courseDao.getAllCourses().first()
        if (existingCourses.isEmpty()) {
            try {
                val courses = remoteDataSource.getCourses()
                courseDao.insertCourses(courses)
                
                courses.forEach { course ->
                    val mockLessons = listOf(
                        Lesson("l1-${course.id}", course.id, "Introduction", false),
                        Lesson("l2-${course.id}", course.id, "Getting Started", false)
                    )
                    lessonDao.insertLessons(mockLessons)
                }
            } catch (_: Exception) {
                // Handle error
            }
        }
    }

    suspend fun getCourseById(courseId: String): Course? {
        return courseDao.getCourseById(courseId)
    }

    fun getLessonsByCourse(courseId: String): Flow<List<Lesson>> {
        return lessonDao.getLessonsByCourse(courseId)
    }

    suspend fun updateLessonCompletion(lessonId: String, courseId: String, isCompleted: Boolean) {
        lessonDao.updateLessonCompletion(lessonId, !isCompleted)

        val allLessonsForCourse = lessonDao.getLessonsByCourseSync(courseId)
        val completedCount = allLessonsForCourse.count { it.isCompleted }
        val totalCount = allLessonsForCourse.size
        val newProgress = if (totalCount > 0) (completedCount * 100) / totalCount else 0

        courseDao.updateCourseProgress(courseId, newProgress)
    }
}
