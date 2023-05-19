package com.ghores.dictionarycompose.di

import com.ghores.dictionarycompose.home.data.remote.DictionaryApi
import com.ghores.dictionarycompose.home.data.repository.DefinitionRepository
import com.ghores.dictionarycompose.home.data.repository.DefinitionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDefinitionRepository(
        dictionaryApi: DictionaryApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DefinitionRepository {
        return DefinitionRepositoryImpl(
            dictionaryApi = dictionaryApi,
            ioDispatcher = ioDispatcher
        )
    }
}