package com.programacionmovilprimeraapp.navigation


import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.programacionmovilprimeraapp.orionnotes.MyApp
import com.programacionmovilprimeraapp.screens.Home.Home
import com.programacionmovilprimeraapp.screens.LoginScreen.Login
import com.programacionmovilprimeraapp.screens.RegisterScreen.Register

@Composable
fun NavigationWrapper(){

    val StartRoute: Route

    if(MyApp.sessionManager.getToken() != null){
        StartRoute = Route.home
    }
    else{
        StartRoute = Route.login
    }

    val backStack = rememberNavBackStack(StartRoute)

    NavDisplay(
        backStack = backStack,

        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },

        entryProvider = entryProvider {
            entry<Route.login>{
                Login(
                    goToRegisterScreen = {
                        backStack.add(Route.register)
                    },

                    goToHome = {
                        backStack.clear()
                        backStack.add(Route.home)
                    }
                )
            }

            entry<Route.register>{
                Register(
                    backToLogin = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<Route.home>{
                Home(

                )
            }
        }
    )
}