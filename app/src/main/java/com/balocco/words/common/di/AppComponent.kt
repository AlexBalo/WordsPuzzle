package com.balocco.words.common.di

import android.app.Application
import com.balocco.words.WordsApplication
import com.balocco.words.home.di.MainActivityViewsBuildersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/** Application component refers to application level modules only. */
@ApplicationScope
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DataModule::class,
        NetworkModule::class,
        MainActivityViewsBuildersModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: WordsApplication)
}