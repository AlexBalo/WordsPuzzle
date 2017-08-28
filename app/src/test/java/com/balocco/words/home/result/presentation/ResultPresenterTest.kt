package com.balocco.words.home.result.presentation

import com.balocco.words.home.result.ResultContract
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ResultPresenterTest {

    @Mock private lateinit var view: ResultContract.View

    private lateinit var presenter: ResultPresenter

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ResultPresenter()
        presenter.setView(view)
    }

    @Test fun `When finished clicked, calls finish on view`() {
        presenter.onFinishClicked()

        verify(view).onFinishRequested()
    }

    @Test fun `When restart clicked, calls restart on view`() {
        presenter.onRestartClicked()

        verify(view).onRestartRequested()
    }

}