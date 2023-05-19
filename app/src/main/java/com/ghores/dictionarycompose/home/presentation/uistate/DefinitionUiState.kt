package com.ghores.dictionarycompose.home.presentation.uistate

import com.ghores.dictionarycompose.home.data.remote.DefinitionResponseModel

data class DefinitionUiState(
    val definition: List<DefinitionResponseModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)