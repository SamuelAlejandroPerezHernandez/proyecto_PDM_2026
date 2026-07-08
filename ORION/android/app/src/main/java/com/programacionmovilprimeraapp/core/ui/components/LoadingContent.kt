package com.programacionmovilprimeraapp.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programacionmovilprimeraapp.orionnotes.R

@Composable
fun LoadingContent(
    padding: PaddingValues
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(horizontal = 32.dp)
        ) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.dustyGrape),
                strokeWidth = 3.dp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "Cargando...",
                fontSize = 14.sp,
                color = colorResource(id = R.color.carbonBlack))
        }
    }
}