package com.programacionmovilprimeraapp

import android.app.Application
import com.programacionmovilprimeraapp.core.data.local.SessionManager

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