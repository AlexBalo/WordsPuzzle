package com.balocco.words.home.result

import com.balocco.words.mvp.BasePresenter
import com.balocco.words.mvp.BaseView

interface ResultContract {

    interface Presenter : BasePresenter<View> {

        fun start()

        fun onCloseClicked()

        fun onRestartClicked()

    }

    interface View : BaseView {

    }

}