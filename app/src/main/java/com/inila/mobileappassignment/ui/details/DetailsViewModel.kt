package com.inila.mobileappassignment.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inila.mobileappassignment.data.model.Course
import com.inila.mobileappassignment.data.model.Lesson
import com.inila.mobileappassignment.data.repository.LearningRepository
import com.inila.mobileappassignment.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailsUiState(
    val course: Course? = null,
    val lessons: Result<List<Lesson>> = Result.Loading
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: LearningRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState

    fun loadData(courseId: String) {
        viewModelScope.launch {
            // Fetch course details
            val course = repository.getCourseById(courseId)
            _uiState.update { it.copy(course = course) }
            
            // Fetch lessons
            repository.getLessonsByCourse(courseId)
                .map { Result.Success(it) as Result<List<Lesson>> }
                .onStart { emit(Result.Loading) }
                .catch { emit(Result.Error("Failed to load lessons")) }
                .collect { lessonsResult ->
                    _uiState.update { it.copy(lessons = lessonsResult) }
                }
        }
    }

    fun toggleLessonCompletion(lessonId: String, courseId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateLessonCompletion(lessonId, courseId, isCompleted)
            // Reload course data to update progress title/state
            val updatedCourse = repository.getCourseById(courseId)
            _uiState.update { it.copy(course = updatedCourse) }
        }
    }
}
