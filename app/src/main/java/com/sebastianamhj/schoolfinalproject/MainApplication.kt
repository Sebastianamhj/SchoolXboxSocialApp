package com.sebastianamhj.schoolfinalproject

import android.app.Application
import com.sebastianamhj.schoolfinalproject.di.firebaseModule
import com.sebastianamhj.schoolfinalproject.di.iconModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(firebaseModule)
            modules(iconModule)
        }
    }
}