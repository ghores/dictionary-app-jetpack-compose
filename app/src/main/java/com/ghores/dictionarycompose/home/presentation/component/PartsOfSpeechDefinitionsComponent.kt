package com.ghores.dictionarycompose.home.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ghores.dictionarycompose.R
import com.ghores.dictionarycompose.home.data.remote.Definition
import com.ghores.dictionarycompose.ui.theme.SkyBlue

@Composable
fun PartsOfSpeechDefinitionsComponent(
    partsOfSpeech: String,
    definitions: List<Definition>?
) {
    Column {
        PartsOfSpeechComponent(
            headerText = partsOfSpeech,
            size = definitions?.size ?: 0,
            color = SkyBlue
        )

        definitions?.forEachIndexed { index, meaning ->
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily(Font(R.font.nunitosans_semibold)),
                            color = Color.Black
                        )
                    ) {
                        append("${index + 1}. ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily(Font(R.font.nunitosans_regular))
                        )
                    ) {
                        append(meaning.definition ?: "")
                    }
                },
                modifier = Modifier
                    .padding(top = 5.dp)
                    .semantics { contentDescription = "definition" }
            )
        }
    }
}