package com.balocco.words.home.di

import android.app.Activity
import com.balocco.words.home.ui.MainActivity
import com.balocco.words.common.di.ActivityScope
import com.balocco.words.common.di.ApplicationScope
import com.balocco.words.data.local.TranslationsStore
import dagger.Module
import dagger.Provides

/* Module that contains specific dependencies for the Main Activity. */
@Module
@ActivityScope
class MainActivityModule {

    @Provides @ActivityScope fun providesActivity(
            activity: MainActivity
    ): Activity = activity

}
