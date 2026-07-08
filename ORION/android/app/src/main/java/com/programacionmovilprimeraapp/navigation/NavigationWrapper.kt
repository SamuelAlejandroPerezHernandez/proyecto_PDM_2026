package com.programacionmovilprimeraapp.navigation


import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.features.account.AccountScreen
import com.programacionmovilprimeraapp.features.home.home.Home
import com.programacionmovilprimeraapp.features.auth.login.Login
import com.programacionmovilprimeraapp.features.auth.register.Register
import com.programacionmovilprimeraapp.features.notes.noteDetail.NoteDetailScreen
import com.programacionmovilprimeraapp.features.notes.noteList.NoteList
import com.programacionmovilprimeraapp.features.task.taskDetail.TaskDetailScreen
import com.programacionmovilprimeraapp.features.task.taskList.TaskList

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
                    goToTaskScreen = {
                        backStack.add(Route.taskList)
                    },

                    goToNoteScreen = {
                        backStack.add(Route.noteList)
                    },

                    goToPerfil = {
                        backStack.add(Route.account)
                    },

                    goToTaskDetail = {
                            id ->
                        backStack.add(Route.taskDetail(id))
                    },

                    goToNoteDetail = {
                            id ->
                        backStack.add(Route.noteDetail(id))
                    }
                )
            }

            entry<Route.taskList>{
                TaskList(
                    goToDetail = {
                            id ->
                        backStack.add(Route.taskDetail(id))
                    },

                    back = {
                        backStack.removeLastOrNull()
                    },

                    goToHome = {
                        backStack.add(Route.home)
                    },

                    goToNoteList = {
                        backStack.add(Route.noteList)
                    },

                    goToPerfil = {
                        backStack.add(Route.account)
                    }
                )
            }

            entry<Route.taskDetail>{
                TaskDetailScreen(
                    id = it.id,

                    backToList = {
                        backStack.removeLastOrNull()
                    },

                    goToHome = {
                        backStack.add(Route.home)
                    },

                    goToTaskList = {
                        backStack.add(Route.taskList)
                    },

                    goToNoteList = {
                        backStack.add(Route.noteList)
                    },

                    goToPerfil = {
                        backStack.add(Route.account)
                    }
                )
            }

            entry<Route.noteList>{
                NoteList(
                    goToDetailNote = {
                            id ->
                        backStack.add(Route.noteDetail(id))
                    },

                    goToHome = {
                        backStack.add(Route.home)
                    },

                    goToTaskList = {
                        backStack.add(Route.taskList)
                    },

                    back = {
                        backStack.removeLastOrNull()
                    },

                    goToPerfil = {
                        backStack.add(Route.account)
                    }
                )
            }

            entry<Route.noteDetail>{
                NoteDetailScreen(
                    id = it.id,

                    goToHome = {
                        backStack.add(Route.home)
                    },

                    goToTaskList = {
                        backStack.add(Route.taskList)
                    },

                    goToNoteList = {
                        backStack.add(Route.noteList)
                    },

                    back = {
                        backStack.removeLastOrNull()
                    },

                    goToPerfil = {
                        backStack.add(Route.account)
                    }
                )
            }

            entry<Route.account>{
                AccountScreen(
                    goToHome = {
                        backStack.add(Route.home)
                    },

                    goToTaskList = {
                        backStack.add(Route.taskList)
                    },

                    goToNoteList = {
                        backStack.add(Route.noteList)
                    },

                    back = {
                        backStack.removeLastOrNull()
                    },

                    goToLogin = {
                        backStack.clear()
                        backStack.add(Route.login)
                    }
                )
            }
        }
    )
}