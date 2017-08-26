package com.balocco.words.common.di

import android.app.Application
import android.content.Context
import com.balocco.words.common.schedulers.RealSchedulerProvider
import com.balocco.words.common.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides

/* Module that contains generic android dependencies. */
@Module
class AppModule {

    @Provides @ApplicationScope
    fun provideContext(
            application: Application
    ): Context = application.applicationContext

    @Provides @ApplicationScope
    fun provideSchedulerProvider(
    ): SchedulerProvider = RealSchedulerProvider()
}