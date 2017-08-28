package com.balocco.words.home.translations.presentation

import android.os.Bundle
import android.view.MotionEvent
import com.balocco.words.data.models.Translation
import com.balocco.words.home.translations.TranslationsContract
import com.balocco.words.home.translations.usecases.CoordinatesArrayUseCase
import com.balocco.words.home.translations.usecases.CoordinatesComparatorUseCase
import com.balocco.words.home.translations.usecases.FlagSelectorUseCase
import com.balocco.words.home.translations.usecases.PositionCoordinateUseCase
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val SOURCE_FLAG_RES = 1
private const val SOURCE_LANGUAGE = "source"
private const val TARGET_FLAG_RES = 2
private const val TARGET_LANGUAGE = "target"
private const val POSITION = 0

class TranslationsPresenterTest {

    @Mock private lateinit var translation: Translation
    @Mock private lateinit var positionCoordinate: PositionCoordinateUseCase
    @Mock private lateinit var coordinatesArray: CoordinatesArrayUseCase
    @Mock private lateinit var coordinatesComparator: CoordinatesComparatorUseCase
    @Mock private lateinit var flagsSelector: FlagSelectorUseCase
    @Mock private lateinit var savedInstanceState: Bundle
    @Mock private lateinit var view: TranslationsContract.View

    private lateinit var presenter: TranslationsPresenter

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TranslationsPresenter(
                translation,
                positionCoordinate,
                coordinatesArray,
                coordinatesComparator,
                flagsSelector)
        presenter.setView(view)
    }

    @Test fun `When started, sets characters in view`() {
        val characters = arrayListOf<String>()
        whenever(translation.characterList).thenReturn(characters)

        presenter.start(savedInstanceState)

        verify(view).setCharacters(characters)
    }

    @Test fun `When started, sets source flag in view`() {
        whenever(translation.sourceLanguage).thenReturn(SOURCE_LANGUAGE)
        whenever(flagsSelector.getFlagResource(SOURCE_LANGUAGE)).thenReturn(SOURCE_FLAG_RES)

        presenter.start(savedInstanceState)

        verify(view).setSourceFlag(SOURCE_FLAG_RES)
    }

    @Test fun `When started, sets target flag in view`() {
        whenever(translation.targetLanguage).thenReturn(TARGET_LANGUAGE)
        whenever(flagsSelector.getFlagResource(TARGET_LANGUAGE)).thenReturn(TARGET_FLAG_RES)

        presenter.start(savedInstanceState)

        verify(view).setTargetFlag(TARGET_FLAG_RES)
    }

    @Test fun `When resumed, starts listening to touch events`() {
        presenter.resume()

        verify(view).startListeningTouchEvents()
    }

    @Test fun `When paused, stops listening to touch events`() {
        presenter.resume()

        verify(view).stopListeningTouchEvents()
    }

    @Test fun `When character touched and action is up, sets empty selected item list`() {
        presenter.onCharacterTouched(POSITION, MotionEvent.ACTION_UP)

        val listCaptor = argumentCaptor<List<Int>>()
        verify(view).setSelectedItems(listCaptor.capture())
        assertThat(listCaptor.firstValue.size).isEqualTo(0)
    }
    
}
