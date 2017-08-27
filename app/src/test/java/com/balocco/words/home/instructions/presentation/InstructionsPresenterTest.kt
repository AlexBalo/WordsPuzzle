package com.balocco.words.home.instructions.presentation

import com.balocco.words.R
import com.balocco.words.common.schedulers.TestSchedulerProvider
import com.balocco.words.common.usecases.ConnectionUseCase
import com.balocco.words.data.local.TranslationsStore
import com.balocco.words.home.instructions.InstructionsContract
import com.balocco.words.home.instructions.usecases.FetchTranslationsUseCase
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class InstructionsPresenterTest {

    @Mock private lateinit var translationsStore: TranslationsStore
    @Mock private lateinit var fetchTranslationsUseCase: FetchTranslationsUseCase
    @Mock private lateinit var connectionUseCase: ConnectionUseCase
    @Mock private lateinit var view: InstructionsContract.View

    private lateinit var schedulerProvider: TestSchedulerProvider
    private lateinit var presenter: InstructionsPresenter

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = TestSchedulerProvider()
        presenter = InstructionsPresenter(
                translationsStore,
                fetchTranslationsUseCase,
                connectionUseCase,
                schedulerProvider)
        presenter.setView(view)
    }

    @Test fun `When start and no connection, shows message`() {
        whenever(connectionUseCase.isDeviceConnected()).thenReturn(false)
        fetchTranslationSuccessfully()

        presenter.start()

        verify(view).showMessage(R.string.instruction_check_connection)
    }

    @Test fun `When start and connected, disables continue button`() {
        whenever(connectionUseCase.isDeviceConnected()).thenReturn(true)
        fetchTranslationSuccessfully()

        presenter.start()

        verify(view).setContinueButtonEnabled(false)
    }

    @Test fun `When start and connected, clears translation in store`() {
        whenever(connectionUseCase.isDeviceConnected()).thenReturn(true)
        fetchTranslationSuccessfully()

        presenter.start()

        verify(translationsStore).clearTranslations()
    }

    @Test fun `When start and connected, fetches translations`() {
        whenever(connectionUseCase.isDeviceConnected()).thenReturn(true)
        fetchTranslationSuccessfully()

        presenter.start()

        verify(fetchTranslationsUseCase).fetchTranslationFromRemoteSource()
    }

    @Test fun `When start and translations fetched successfully, enables button`() {
        whenever(connectionUseCase.isDeviceConnected()).thenReturn(true)
        fetchTranslationSuccessfully()

        presenter.start()

        verify(view).setContinueButtonEnabled(true)
    }

    @Test fun `When start and translations fetched with error, show error message`() {
        whenever(connectionUseCase.isDeviceConnected()).thenReturn(true)
        fetchTranslationWithError()

        presenter.start()

        verify(view).showMessage(R.string.instruction_error_while_fetching)
    }

    private fun fetchTranslationSuccessfully() =
            whenever(fetchTranslationsUseCase.fetchTranslationFromRemoteSource())
                    .thenReturn(Completable.complete())

    private fun fetchTranslationWithError() =
            whenever(fetchTranslationsUseCase.fetchTranslationFromRemoteSource())
                    .thenReturn(Completable.error(Throwable()))
}