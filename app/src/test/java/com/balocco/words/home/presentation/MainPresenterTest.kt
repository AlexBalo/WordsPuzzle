package com.balocco.words.home.presentation

import android.os.Bundle
import com.balocco.words.R
import com.balocco.words.common.extensions.AnimationType
import com.balocco.words.data.local.TranslationsStore
import com.balocco.words.data.models.Translation
import com.balocco.words.home.MainContract
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock private lateinit var translationsStore: TranslationsStore
    @Mock private lateinit var view: MainContract.View
    @Mock private lateinit var savedInstanceState: Bundle
    @Mock private lateinit var translation: Translation

    private lateinit var presenter: MainPresenter

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(translationsStore)
        presenter.setView(view)
    }

    @Test fun `When started, sets expected title`() {
        presenter.start(savedInstanceState)

        verify(view).setTitle(R.string.app_name)
    }

    @Test fun `When started with null bundle and no translations, updates progress with default`() {
        presenter.start(null)

        verify(view).updateProgress(DEFAULT_PROGRESS_MAX_VALUE, DEFAULT_PROGRESS_VALUE)
    }

    @Test fun `When started with null bundle and no translations, shows instructions`() {
        presenter.start(null)

        verify(view).showInstructions(AnimationType.NONE)
    }

    @Test fun `When restore instance state, sets value from bundle`() {
        val index = 5
        whenever(savedInstanceState.getInt(KEY_TRANSLATION_INDEX)).thenReturn(index)

        presenter.onRestoreInstanceState(savedInstanceState)

        assertThat(presenter.translationIndex).isEqualTo(index)
    }

    @Test fun `When saved instance state, stores index in bundle`() {
        presenter.onSavedInstanceState(savedInstanceState)

        verify(savedInstanceState).putInt(KEY_TRANSLATION_INDEX, presenter.translationIndex)
    }

    @Test fun `When instructions ready, shows character grid`() {
        whenever(translationsStore.fetchTranslationWithIndex(any())).thenReturn(translation)

        presenter.onInstructionsReady()

        verify(view).showTranslation(translation, AnimationType.SLIDE)
    }

    @Test fun `When instructions ready, updates progress`() {
        val index = presenter.translationIndex
        val count = 5
        whenever(translationsStore.getTranslationCount()).thenReturn(count)

        presenter.onInstructionsReady()

        verify(view).updateProgress(count, index)
    }

    @Test fun `When restart requested, clears translations from store`() {
        presenter.onRestartRequested()

        verify(translationsStore).clearTranslations()
    }

    @Test fun `When restart requested, resets index to default`() {
        presenter.onRestartRequested()

        assertThat(presenter.translationIndex).isEqualTo(DEFAULT_INDEX)
    }

    @Test fun `When restart requested, shows instructions`() {
        presenter.onRestartRequested()

        verify(view).showInstructions(AnimationType.FADE)
    }

    @Test fun `When restart requested, updates progress with default`() {
        presenter.onRestartRequested()

        verify(view).updateProgress(DEFAULT_PROGRESS_MAX_VALUE, DEFAULT_PROGRESS_VALUE)
    }

    @Test fun `When finish, calls finish on view`() {
        presenter.onFinishRequested()

        verify(view).finish()
    }
}