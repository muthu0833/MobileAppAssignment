package com.inila.mobileappassignment.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inila.mobileappassignment.data.model.Course
import com.inila.mobileappassignment.data.repository.LearningRepository
import com.inila.mobileappassignment.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class DashboardUiState(
    val result: Result<List<Course>> = Result.Loading
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    repository: LearningRepository
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = repository.allCourses
        .map { Result.Success(it) as Result<List<Course>> }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error("Failed to load courses")) }
        .map { DashboardUiState(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardUiState())
}
