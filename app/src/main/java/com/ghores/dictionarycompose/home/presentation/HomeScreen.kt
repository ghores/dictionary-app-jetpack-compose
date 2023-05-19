package com.ghores.dictionarycompose.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ghores.dictionarycompose.R
import com.ghores.dictionarycompose.common.UiEvents
import com.ghores.dictionarycompose.home.data.remote.Meaning
import com.ghores.dictionarycompose.home.presentation.component.EmptyComponent
import com.ghores.dictionarycompose.home.presentation.component.LoadingComponent
import com.ghores.dictionarycompose.home.presentation.component.PartsOfSpeechDefinitionsComponent
import com.ghores.dictionarycompose.home.presentation.component.PronunciationComponent
import com.ghores.dictionarycompose.home.presentation.component.SearchTextFieldComponent
import com.ghores.dictionarycompose.home.presentation.uistate.DefinitionUiState
import com.ghores.dictionarycompose.ui.theme.DictionaryComposeTheme
import com.ghores.dictionarycompose.ui.theme.SkyBlue
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    definitionViewModel: DefinitionViewModel = hiltViewModel()
) {
    //val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        definitionViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackBarEvent -> {
                    // scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    val definitionUiState = definitionViewModel.definitionUiState.collectAsState().value

    val typedWord = definitionViewModel.typedWord.value

    val definitions =
        if (definitionUiState.definition?.isNotEmpty() == true) definitionUiState.definition[0].meanings
            ?: emptyList()
        else emptyList()

    DictionaryComposeTheme {

        Scaffold(
            // scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Dictionary",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunitosans_bold)),
                            color = Color.White
                        )

                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = SkyBlue
                    )
                )
            }
        ) { paddingValues ->

            HomeContent(
                definitionUiState = definitionUiState,

                typedWord = typedWord,

                setWordToBeSearched = { word ->
                    definitionViewModel.setTypedWord(typedWord = word)
                },

                searchWord = {
                    definitionViewModel.getDefinition()
                },

                meanings = definitions,

                paddingValues = paddingValues
            )
        }
    }
}

@Composable
fun HomeContent(
    definitionUiState: DefinitionUiState,
    typedWord: String,
    setWordToBeSearched: (String) -> Unit,
    searchWord: () -> Unit,
    meanings: List<Meaning>,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        LazyColumn(contentPadding = PaddingValues(14.dp)) {
            item {
                SearchTextFieldComponent(
                    typedWord = typedWord,
                    setWordToBeSearched = setWordToBeSearched,
                    searchWord = searchWord
                )
            }

            if (definitionUiState.isLoading || definitionUiState.definition?.isEmpty() == true) {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingComponent(
                            isLoading = definitionUiState.isLoading
                        )

                        EmptyComponent(
                            isLoading = definitionUiState.isLoading,
                            definition = definitionUiState.definition
                        )
                    }
                }
            }

            if (!definitionUiState.isLoading && !definitionUiState.definition.isNullOrEmpty()) {
                item {
                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )

                    PronunciationComponent(
                        word = definitionUiState.definition[0].word ?: "",
                        phonetic = definitionUiState.definition[0].phonetic ?: "---"
                    )
                }

                items(meanings) { meaning ->
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    PartsOfSpeechDefinitionsComponent(
                        partsOfSpeech = meaning.partOfSpeech ?: "",
                        definitions = meaning.definitions ?: emptyList()
                    )
                }
            }
        }
    }
}