package tech.dzolotov.todolist

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import tech.dzolotov.todolist.di.appModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i(MainApplication::class.java.name, "STARTED")
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule)
        }
    }
}