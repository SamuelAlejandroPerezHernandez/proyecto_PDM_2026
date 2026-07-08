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

    @Serializable
    data object taskList: Route()

    @Serializable
    data object noteList: Route()

    @Serializable
    data class taskDetail(val id: String): Route(){

    }

    @Serializable
    data class noteDetail(val id: String): Route()

    @Serializable
    data object account : Route()

}