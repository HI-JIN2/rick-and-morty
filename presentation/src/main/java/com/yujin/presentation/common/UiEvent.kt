package com.yujin.presentation.common

interface UiEvent {
    data class ShowToast(val message: String) : UiEvent
    object NavigateBack : UiEvent
}