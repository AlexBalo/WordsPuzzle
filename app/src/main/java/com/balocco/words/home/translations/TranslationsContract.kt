package com.balocco.words.home.translations

import android.os.Bundle
import android.support.annotation.DrawableRes
import com.balocco.words.mvp.BasePresenter
import com.balocco.words.mvp.BaseView

interface TranslationsContract {

    interface Presenter : BasePresenter<View> {

        fun start(savedInstanceState: Bundle?)

        fun resume()

        fun pause()

        fun onSavedInstanceState(outState: Bundle?)

        fun onCharacterTouched(position: Int, eventAction: Int)

    }

    interface View : BaseView {

        fun startListeningTouchEvents()

        fun stopListeningTouchEvents()

        fun setCharacters(characters: List<String>)

        fun setSourceFlag(@DrawableRes resource: Int)

        fun setTargetFlag(@DrawableRes resource: Int)

        fun setSelectedItems(positions: List<Int>)

        fun setSolutionItems(positions: List<Int>)

        fun updateSolutionsRatio(found: Int, expected: Int)

        fun showNextButton()

    }

}