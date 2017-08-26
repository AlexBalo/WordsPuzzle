package com.balocco.words.home

import android.os.Bundle
import android.support.annotation.StringRes
import com.balocco.words.data.models.Translation
import com.balocco.words.mvp.BasePresenter
import com.balocco.words.mvp.BaseView

interface MainContract {

    interface Presenter : BasePresenter<View> {

        fun start(savedInstanceState: Bundle?)

        fun onInstructionsReady()

    }

    interface View : BaseView {

        fun setTitle(@StringRes titleResource: Int)

        fun showInstructions()

        fun showTranslation(translation: Translation)

        fun showMessage(@StringRes messageRes: Int)
    }

}