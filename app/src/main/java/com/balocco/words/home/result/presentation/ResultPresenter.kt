package com.balocco.words.home.result.presentation

import com.balocco.words.home.result.ResultContract
import com.balocco.words.mvp.ReactivePresenter
import javax.inject.Inject

class ResultPresenter @Inject constructor(
) : ReactivePresenter(), ResultContract.Presenter {

    private lateinit var view: ResultContract.View

    override fun setView(view: ResultContract.View) {
        this.view = view
    }

    override fun start() {
    }

    override fun onCloseClicked() {
    }

    override fun onRestartClicked() {
    }

}