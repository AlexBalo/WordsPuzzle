package com.balocco.words.home.di

import com.balocco.words.common.di.ActivityScope
import com.balocco.words.home.instructions.ui.InstructionsFragment
import com.balocco.words.home.result.ui.ResultFragment
import com.balocco.words.home.translations.ui.TranslationsFragment
import com.balocco.words.home.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@ActivityScope
abstract class MainActivityViewsBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeMainActivityInjector(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeInstructionsFragmentInjector(): InstructionsFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeTranslationsFragmentInjector(): TranslationsFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeResultFragmentInjector(): ResultFragment

}