package com.programacionmovilprimeraapp.orionnotes

import android.app.Application
import com.programacionmovilprimeraapp.data.local.SessionManager

class MyApp: Application(){
    companion object {
        // Esta es la caja fuerte global donde guardamos el manager ya inicializado
        lateinit var sessionManager: SessionManager
            private set
    }

    override fun onCreate() {
        super.onCreate()

        sessionManager = SessionManager.Companion.getInstance(this)
    }
}