package com.ghores.dictionarycompose.common

sealed class UiEvents {
    data class SnackBarEvent(val message: String) : UiEvents()
}