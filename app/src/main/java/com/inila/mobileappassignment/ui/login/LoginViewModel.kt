package com.inila.mobileappassignment.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inila.mobileappassignment.data.repository.AuthRepository
import com.inila.mobileappassignment.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val result: Result<Unit>? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String) {
        if (!isValidEmail(email)) {
            _uiState.value = LoginUiState(Result.Error("Invalid email format"))
            return
        }
        if (password.isBlank()) {
            _uiState.value = LoginUiState(Result.Error("Password cannot be empty"))
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState(Result.Loading)
            try {
                val success = authRepository.login(email, password)
                if (success) {
                    _uiState.value = LoginUiState(Result.Success(Unit))
                } else {
                    _uiState.value = LoginUiState(Result.Error("Invalid email or password"))
                }
            } catch (_: Exception) {
                _uiState.value = LoginUiState(Result.Error("An unexpected error occurred"))
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
