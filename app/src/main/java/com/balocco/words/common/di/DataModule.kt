package com.balocco.words.common.di

import android.content.Context
import com.balocco.words.data.local.TranslationsStore
import dagger.Module
import dagger.Provides

/* Module that contains dependencies to access local data. */
@Module
class DataModule {

    @Provides @ApplicationScope fun providesTranslationStore(
    ): TranslationsStore = TranslationsStore()

}