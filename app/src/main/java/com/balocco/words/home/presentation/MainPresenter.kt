package com.balocco.words.home.presentation

import android.os.Bundle
import com.balocco.words.R
import com.balocco.words.data.local.TranslationsStore
import com.balocco.words.home.MainContract
import com.balocco.words.mvp.ReactivePresenter
import javax.inject.Inject

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
            view.showInstructions()
            return
        }
    }

    override fun onInstructionsReady() {
        val translation = translationsStore.fetchTranslationWithIndex(translationIndex)
        if (translation != null) {
            view.showTranslation(translation)
        }
    }

}