package com.programacionmovilprimeraapp.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


@Composable
fun ErrorContent(
    error: String?,
    padding: PaddingValues,
    onRetry: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color(0xFF1E293B))
    ) {
        Text(text = error?: "")

        Button(
            onClick = { onRetry() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE69510)
            )
        ) {
            Text(text = "Reintentar",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}