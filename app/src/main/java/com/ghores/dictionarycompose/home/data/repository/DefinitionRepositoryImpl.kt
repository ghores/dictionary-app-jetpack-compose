package com.ghores.dictionarycompose.home.data.repository

import com.ghores.dictionarycompose.common.Resource
import com.ghores.dictionarycompose.di.IoDispatcher
import com.ghores.dictionarycompose.home.data.remote.DefinitionResponseModel
import com.ghores.dictionarycompose.home.data.remote.DictionaryApi
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefinitionRepositoryImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : DefinitionRepository {
    override suspend fun getDefinition(word: String): Flow<Resource<List<DefinitionResponseModel>>> =
        flow {
            emit(Resource.Loading())
            when (val response = dictionaryApi.getDefinition(word = word)) {
                is NetworkResponse.Success -> {
                    val definitionResponse = response.body

                    emit(Resource.Success(data = definitionResponse))
                }
                is NetworkResponse.ServerError -> {
                    emit(
                        Resource.Error(
                            message = response.body?.message ?: "Try again with a new word!"
                        )
                    )
                }
                is NetworkResponse.NetworkError -> {
                    emit(
                        Resource.Error(
                            message = "Please check if you're connected to the internet or try again later"
                        )
                    )
                }
                is NetworkResponse.UnknownError -> {
                    emit(Resource.Error(message = "Unknown error occurred while fetching definition"))
                }
            }
        }.flowOn(ioDispatcher)
}