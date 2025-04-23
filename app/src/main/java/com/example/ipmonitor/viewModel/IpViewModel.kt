package com.example.ipmonitor.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ipmonitor.repository.IpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class IpViewModel @Inject constructor(
    private val repository: IpRepository
) : ViewModel() {

    sealed class UiState {
        data object Default : UiState()
        data class Success(val ip: String) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Default)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var updateJob: Job? = null

    fun fetchIpAddress() {
        when (_uiState.value) {
            is UiState.Success -> resetToDefault()
            is UiState.Error -> resetToDefault()
            UiState.Default -> startIpFetching()
        }
    }

    private fun startIpFetching() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            handleIpRequest()
            startAutoRefresh()
        }
    }

    private fun startAutoRefresh() {
        updateJob = viewModelScope.launch {
            while (currentCoroutineContext().isActive) {
                delay(5000)
                Log.d("TIC", "5 SEC")
                handleIpRequest()
            }
        }
    }

    private suspend fun handleIpRequest() {
        try {
            val ip = repository.getIpAddress()
            Log.d("IP", ip)
            _uiState.value = UiState.Success(ip)
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
            _uiState.value = UiState.Error(translateException(e))
        }
    }

    private fun translateException(e: Exception): String {
        return when (e) {
            is UnknownHostException -> "Ошибка подключения.\nПроверьте интернет"
            is SocketTimeoutException -> "Таймаут соединения.\nПопробуйте позже"
            is IOException -> "Сетевая ошибка:\n${e.localizedMessage ?: "нет данных"}"
            else -> "Неизвестная ошибка:\n${e.localizedMessage ?: "нет данных"}"
        }
    }

    private fun resetToDefault() {
        updateJob?.cancel()
        _uiState.value = UiState.Default
    }

    override fun onCleared() {
        super.onCleared()
        updateJob?.cancel()
    }

}