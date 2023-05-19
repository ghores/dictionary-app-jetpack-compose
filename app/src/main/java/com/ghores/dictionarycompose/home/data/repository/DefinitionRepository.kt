package com.ghores.dictionarycompose.home.data.repository

import com.ghores.dictionarycompose.common.Resource
import com.ghores.dictionarycompose.home.data.remote.DefinitionResponseModel
import kotlinx.coroutines.flow.Flow

interface DefinitionRepository {
    suspend fun getDefinition(word: String): Flow<Resource<List<DefinitionResponseModel>>>
}