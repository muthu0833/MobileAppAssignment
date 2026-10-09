package com.inila.mobileappassignment

import com.inila.mobileappassignment.data.local.AppDatabase
import com.inila.mobileappassignment.data.local.CourseDao
import com.inila.mobileappassignment.data.local.LessonDao
import com.inila.mobileappassignment.data.model.Lesson
import com.inila.mobileappassignment.data.remote.CourseRemoteDataSource
import com.inila.mobileappassignment.data.repository.LearningRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class LearningRepositoryTest {

    private lateinit var repository: LearningRepository
    private val courseDao = mock(CourseDao::class.java)
    private val lessonDao = mock(LessonDao::class.java)
    private val remoteDataSource = mock(CourseRemoteDataSource::class.java)
    private val database = mock(AppDatabase::class.java)

    @Before
    fun setup() {
        repository = LearningRepository(database, courseDao, lessonDao, remoteDataSource)
    }

    @Test
    fun `test progress calculation logic`() = runBlocking {
        // Arrange: Mock data for 2 lessons, 1 completed
        val courseId = "1"
        val lessons = listOf(
            Lesson("l1", courseId, "Intro", true),
            Lesson("l2", courseId, "Advanced", false)
        )
        `when`(lessonDao.getLessonsByCourseSync(courseId)).thenReturn(lessons)

        // Act
        repository.updateLessonCompletion("l2", courseId, false)

        // Assert: 2 total lessons, 1 completed = 50%
        verify(courseDao).updateCourseProgress(courseId, 50)
    }
}
