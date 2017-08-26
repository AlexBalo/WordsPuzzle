package com.balocco.words.home.translations.presentation

import android.view.MotionEvent
import com.balocco.words.data.models.Translation
import com.balocco.words.data.models.WordCoordinate
import com.balocco.words.home.translations.TranslationsContract
import com.balocco.words.home.translations.usecases.CoordinatesArrayUseCase
import com.balocco.words.home.translations.usecases.CoordinatesComparatorUseCase
import com.balocco.words.home.translations.usecases.PositionCoordinateUseCase
import com.balocco.words.mvp.ReactivePresenter
import javax.inject.Inject

private const val INVALID_FIRST_CHARACTER = -1

class TranslationsPresenter @Inject constructor(
        translation: Translation,
        private val positionCoordinate: PositionCoordinateUseCase,
        private val coordinatesArray: CoordinatesArrayUseCase,
        private val coordinatesComparator: CoordinatesComparatorUseCase
) : ReactivePresenter(), TranslationsContract.Presenter {

    private lateinit var view: TranslationsContract.View
    private lateinit var pivotCoordinate: WordCoordinate

    private var pivotCharacterPosition = INVALID_FIRST_CHARACTER
    private val gridSize = translation.gridSize

    override fun setView(view: TranslationsContract.View) {
        this.view = view
    }

    override fun resume() {
        view.startListeningTouchEvents()
    }

    override fun pause() {
        view.stopListeningTouchEvents()
    }

    override fun onCharacterTouched(position: Int, eventAction: Int) {
        if (eventAction == MotionEvent.ACTION_UP || eventAction == MotionEvent.ACTION_OUTSIDE) {
            pivotCharacterPosition = INVALID_FIRST_CHARACTER
            setSelectedItems(emptyList())
            return
        }

        if (eventAction == MotionEvent.ACTION_DOWN && isPositionInvalid()) {
            pivotCharacterPosition = position
            pivotCoordinate = getCoordinatesFromPosition(position)
        }

        if (position == pivotCharacterPosition) {
            setSelectedItems(arrayListOf(pivotCharacterPosition))
            return
        }

        val newCoordinate = getCoordinatesFromPosition(position)
        calculateSelectedCharacters(newCoordinate)
    }

    private fun calculateSelectedCharacters(coordinate: WordCoordinate) {
        if (coordinatesComparator.isCoordinateAbovePivot(pivotCoordinate, coordinate)) {
            setSelectedItems(arrayListOf(pivotCharacterPosition))
            return
        }

        if (coordinatesComparator.isCoordinateLeftOfPivot(pivotCoordinate, coordinate)) {
            setSelectedItems(arrayListOf(pivotCharacterPosition))
            return
        }

        if (coordinatesComparator.isCoordinateRightOfPivotOnSameRow(pivotCoordinate, coordinate)) {
            selectItemsInRow(coordinate)
            return
        }

        if (coordinatesComparator.isCoordinateBelowPivotOnSameColumn(pivotCoordinate, coordinate)) {
            selectItemsInColumn(coordinate)
            return
        }

        selectItemsDiagonally(coordinate)
    }

    private fun isPositionInvalid() = pivotCharacterPosition == INVALID_FIRST_CHARACTER

    private fun selectItemsInRow(coordinate: WordCoordinate) {
        val coordinates = coordinatesArray.calculateCoordinatesOnSameRow(
                pivotCoordinate, coordinate)
        val positions = getPositionsFromCoordinates(coordinates)
        setSelectedItems(positions)
    }

    private fun selectItemsInColumn(coordinate: WordCoordinate) {
        val coordinates = coordinatesArray.calculateCoordinatesOnSameColumn(
                pivotCoordinate, coordinate)
        val positions = getPositionsFromCoordinates(coordinates)
        setSelectedItems(positions)
    }

    private fun selectItemsDiagonally(coordinate: WordCoordinate) {
        val coordinates = coordinatesArray.calculateCoordinatesDiagonally(
                pivotCoordinate, coordinate)
        val positions = getPositionsFromCoordinates(coordinates)
        setSelectedItems(positions)
    }

    private fun getPositionsFromCoordinates(coordinates: List<WordCoordinate>): List<Int> =
            positionCoordinate.positionsFromCoordinates(gridSize, coordinates)

    private fun getCoordinatesFromPosition(position: Int) =
            positionCoordinate.coordinateFromPosition(gridSize, position)

    private fun setSelectedItems(positions: List<Int>) =
            view.setSelectedItems(positions)

}