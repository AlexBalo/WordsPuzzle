package com.balocco.words.home.instructions.presentation

import com.balocco.words.R
import com.balocco.words.common.usecases.ConnectionUseCase
import com.balocco.words.data.local.TranslationsStore
import com.balocco.words.home.instructions.InstructionsContract
import com.balocco.words.home.usecases.FetchTranslationsUseCase
import com.balocco.words.mvp.ReactivePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class InstructionsPresenter @Inject constructor(
        private val translationsStore: TranslationsStore,
        private val fetchTranslationsUseCase: FetchTranslationsUseCase,
        private val connectionUseCase: ConnectionUseCase
) : ReactivePresenter(), InstructionsContract.Presenter {

    private lateinit var view: InstructionsContract.View

    override fun setView(view: InstructionsContract.View) {
        this.view = view
    }

    override fun start() {

        if (!connectionUseCase.isDeviceConnected()) {
            view.showMessage(R.string.instruction_check_connection)
            return
        }

        view.setContinueButtonEnabled(false)

        translationsStore.clearTranslations()

        addDisposable(
                fetchTranslationsUseCase.fetchTranslationFromRemoteSource()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            view.setContinueButtonEnabled(true)
                        }, {
                            view.showMessage(R.string.instruction_error_while_fetching)
                        })
        )
    }

}