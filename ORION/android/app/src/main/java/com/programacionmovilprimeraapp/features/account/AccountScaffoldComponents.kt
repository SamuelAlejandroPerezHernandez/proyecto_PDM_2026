package com.programacionmovilprimeraapp.features.account

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.programacionmovilprimeraapp.orionnotes.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTopBar(
    back: () -> Unit
){
    TopAppBar(
        title = {},

        navigationIcon = {
            IconButton(
                onClick = { back() },
                modifier = Modifier
                    .size(40.dp)
            ){
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        },

        actions = {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(40.dp)
            ){
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource( id = R.color.carbonBlack )
        )
    )
}