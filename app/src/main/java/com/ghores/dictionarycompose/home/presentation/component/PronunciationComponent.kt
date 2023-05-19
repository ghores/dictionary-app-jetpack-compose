package com.ghores.dictionarycompose.home.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghores.dictionarycompose.R
import com.ghores.dictionarycompose.ui.theme.SkyBlue

@Composable
fun PronunciationComponent(
    word: String,
    phonetic: String
) {
    Column {
        Text(
            text = word,
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.nunitosans_extrabold))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = phonetic,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.nunitosans_bold)),
                color = SkyBlue
            )
        }
    }
}