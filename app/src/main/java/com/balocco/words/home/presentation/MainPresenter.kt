package com.balocco.words.home.presentation

import android.os.Bundle
import com.balocco.words.R
import com.balocco.words.data.local.TranslationsStore
import com.balocco.words.home.MainContract
import com.balocco.words.mvp.ReactivePresenter
import javax.inject.Inject

private const val KEY_TRANSLATION_INDEX = "KEY_TRANSLATION_INDEX"
private const val DEFAULT_PROGRESS_MAX_VALUE = 1
private const val DEFAULT_PROGRESS_VALUE = 0

class MainPresenter @Inject constructor(
        private val translationsStore: TranslationsStore
) : ReactivePresenter(), MainContract.Presenter {

    private lateinit var view: MainContract.View
    private var translationIndex = 0

    override fun setView(view: MainContract.View) {
        this.view = view
    }

    override fun start(savedInstanceState: Bundle?) {
        view.setTitle(R.string.app_name)

        if (savedInstanceState == null || !translationsStore.hasTranslations()) {
            view.updateProgress(DEFAULT_PROGRESS_MAX_VALUE, DEFAULT_PROGRESS_VALUE)
            view.showInstructions()
            return
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            translationIndex = savedInstanceState.getInt(KEY_TRANSLATION_INDEX)
        }
    }

    override fun onSavedInstanceState(outBundle: Bundle?) {
        outBundle?.putInt(KEY_TRANSLATION_INDEX, translationIndex)
    }

    override fun onInstructionsReady() {
        showTranslationGrid()
        view.updateProgress(translationsStore.getTranslationCount(), translationIndex)
    }

    override fun onGridCompleted() {
        translationIndex++
        showTranslationGrid()
        view.updateProgress(translationsStore.getTranslationCount(), translationIndex)
    }

    private fun showTranslationGrid() {
        val translation = translationsStore.fetchTranslationWithIndex(translationIndex)
        if (translation != null) {
            view.showTranslation(translation)
        }
    }

}