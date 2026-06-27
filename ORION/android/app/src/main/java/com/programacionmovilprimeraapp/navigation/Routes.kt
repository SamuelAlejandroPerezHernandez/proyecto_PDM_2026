package com.programacionmovilprimeraapp.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Route: NavKey{

    @Serializable
    data object login: Route()

    @Serializable
    data object register: Route()

    @Serializable
    data object home: Route()


}