package com.balocco.words.home.translations.presentation

import com.balocco.words.R
import com.balocco.words.home.translations.CharacterItemContract
import com.balocco.words.home.translations.usecases.CharacterTextSizeUseCase
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val GRID_SIZE = 5
private const val TEXT_SIZE = 15
private const val TEXT = "text"
private const val POSITION = 1

class CharacterItemPresenterTest {

    @Mock private lateinit var characterTextSizeUseCase: CharacterTextSizeUseCase
    @Mock private lateinit var view: CharacterItemContract.View

    private lateinit var presenter: CharacterItemPresenter

    private val selectedItems = arrayListOf<Int>()
    private val solutionItems = arrayListOf<Int>()

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = CharacterItemPresenter(
                GRID_SIZE,
                characterTextSizeUseCase)
        presenter.setView(view)
    }

    @After fun tearDown() {
        selectedItems.clear()
        solutionItems.clear()
    }

    @Test fun `When started, sets character text size`() {
        whenever(characterTextSizeUseCase.getTextSizeBasedOnGrid(GRID_SIZE)).thenReturn(TEXT_SIZE)

        presenter.start()

        verify(view).setCharacterTextSize(TEXT_SIZE)
    }

    @Test fun `When character updated, sets character text`() {
        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems)

        verify(view).setCharacterText(TEXT)
    }

    @Test fun `When character updated and selected contains position, shows selected`() {
        selectedItems.add(POSITION)

        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems)

        verify(view).showSelector()
    }

    @Test fun `When character updated and selected contains position, set white text color`() {
        selectedItems.add(POSITION)

        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems)

        verify(view).setCharacterTextColor(android.R.color.white)
    }

    @Test fun `When character updated and selected doesn't contain position, hides selected`() {
        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems)

        verify(view).hideSelector()
    }

    @Test fun `When character updated and selected doesn't contain position, set black text color`() {
        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems)

        verify(view).setCharacterTextColor(android.R.color.black)
    }

    @Test fun `When character updated and solutions contains position, set cell color to expected`() {
        solutionItems.add(POSITION)

        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems)

        verify(view).setCellBackgroundColor(R.color.colorSolution)
    }

    @Test fun `When character updated and solutions doesn't contain position, set cell color to transparent`() {
        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems)

        verify(view).setCellBackgroundColor(android.R.color.transparent)
    }
}