package com.balocco.words.home.instructions

import android.os.Bundle
import android.support.annotation.StringRes
import com.balocco.words.mvp.BasePresenter
import com.balocco.words.mvp.BaseView

interface InstructionsContract {

    interface Presenter : BasePresenter<View> {

        fun start()

    }

    interface View : BaseView {

        fun setContinueButtonEnabled(enabled: Boolean)

        fun showMessage(@StringRes messageRes: Int)

    }

}