package com.balocco.words.home

import android.os.Bundle
import android.support.annotation.StringRes
import com.balocco.words.common.extensions.AnimationType
import com.balocco.words.data.models.Translation
import com.balocco.words.mvp.BasePresenter
import com.balocco.words.mvp.BaseView

interface MainContract {

    interface Presenter : BasePresenter<View> {

        fun start(savedInstanceState: Bundle?)

        fun onRestoreInstanceState(savedInstanceState: Bundle?)

        fun onSavedInstanceState(outBundle: Bundle?)

        fun onInstructionsReady()

        fun onGridCompleted()

        fun onRestartRequested()

        fun onFinishRequested()

    }

    interface View : BaseView {

        fun setTitle(@StringRes titleResource: Int)

        fun showInstructions(animationType: AnimationType)

        fun showTranslation(translation: Translation, animationType: AnimationType)

        fun showResult(animationType: AnimationType)

        fun updateProgress(maxValue: Int, currentValue: Int)

        fun showMessage(@StringRes messageRes: Int)

        fun finish()

    }

}