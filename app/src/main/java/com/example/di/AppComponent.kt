package com.example.di

import com.example.context.LiveScoreApplication
import com.example.di.net.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: LiveScoreApplication): Builder
        fun build(): AppComponent
    }
}