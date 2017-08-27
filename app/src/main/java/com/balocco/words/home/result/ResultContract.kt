package com.balocco.words.home.result

import com.balocco.words.mvp.BasePresenter
import com.balocco.words.mvp.BaseView

interface ResultContract {

    interface Presenter : BasePresenter<View> {

        fun onFinishClicked()

        fun onRestartClicked()

    }

    interface View : BaseView {

        fun onRestartRequested()

        fun onFinishRequested()

    }

}