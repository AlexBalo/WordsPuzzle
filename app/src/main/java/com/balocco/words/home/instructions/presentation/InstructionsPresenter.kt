package com.balocco.words.home.instructions.presentation

import com.balocco.words.R
import com.balocco.words.common.schedulers.SchedulerProvider
import com.balocco.words.common.usecases.ConnectionUseCase
import com.balocco.words.data.local.TranslationsStore
import com.balocco.words.home.instructions.InstructionsContract
import com.balocco.words.home.instructions.usecases.FetchTranslationsUseCase
import com.balocco.words.mvp.ReactivePresenter
import javax.inject.Inject

class InstructionsPresenter @Inject constructor(
        private val translationsStore: TranslationsStore,
        private val fetchTranslationsUseCase: FetchTranslationsUseCase,
        private val connectionUseCase: ConnectionUseCase,
        private val schedulerProvider: SchedulerProvider
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
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            view.setContinueButtonEnabled(true)
                        }, {
                            view.showMessage(R.string.instruction_error_while_fetching)
                        })
        )
    }

}