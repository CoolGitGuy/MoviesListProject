package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.initKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
