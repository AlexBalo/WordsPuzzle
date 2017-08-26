package com.balocco.words.home.translations.presentation

import android.os.Bundle
import android.view.MotionEvent
import com.balocco.words.data.models.Translation
import com.balocco.words.data.models.WordCoordinate
import com.balocco.words.home.translations.TranslationsContract
import com.balocco.words.home.translations.usecases.CoordinatesArrayUseCase
import com.balocco.words.home.translations.usecases.CoordinatesComparatorUseCase
import com.balocco.words.home.translations.usecases.PositionCoordinateUseCase
import com.balocco.words.home.translations.viewmodels.Solution
import com.balocco.words.mvp.ReactivePresenter
import javax.inject.Inject

private const val INVALID_FIRST_CHARACTER = -1
private const val KEY_SOLUTIONS = "KEY_SOLUTIONS"

class TranslationsPresenter @Inject constructor(
        private val translation: Translation,
        private val positionCoordinate: PositionCoordinateUseCase,
        private val coordinatesArray: CoordinatesArrayUseCase,
        private val coordinatesComparator: CoordinatesComparatorUseCase
) : ReactivePresenter(), TranslationsContract.Presenter {

    private lateinit var view: TranslationsContract.View
    private lateinit var pivotCoordinate: WordCoordinate

    private var pivotCharacterPosition = INVALID_FIRST_CHARACTER
    private val gridSize = translation.gridSize
    private var lastPositionsSelection = arrayListOf<Int>()
    private var solutions = arrayListOf<Solution>()

    override fun setView(view: TranslationsContract.View) {
        this.view = view
    }

    override fun start(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            solutions = savedInstanceState.getParcelableArrayList(KEY_SOLUTIONS)
        }
    }

    override fun resume() {
        verifySolutions()
        view.startListeningTouchEvents()
    }

    override fun pause() {
        view.stopListeningTouchEvents()
    }

    override fun onSavedInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(KEY_SOLUTIONS, solutions)
    }

    override fun onCharacterTouched(position: Int, eventAction: Int) {
        if (eventAction == MotionEvent.ACTION_UP) {
            verifySolutions()
            clearSelection()
            return
        }

        if (eventAction == MotionEvent.ACTION_OUTSIDE) {
            clearSelection()
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

    private fun clearSelection() {
        lastPositionsSelection.clear()
        pivotCharacterPosition = INVALID_FIRST_CHARACTER
        setSelectedItems(emptyList())
    }

    private fun verifySolutions() {
        if (isSolutionAlreadyFound()) {
            return
        }

        val solutionsPositions = arrayListOf<List<Int>>()
        val allSolutionsCoordinates = translation.getAllSolutionsCoordinates()
        val size = allSolutionsCoordinates.size
        for (i in 0 until size) {
            solutionsPositions.add(getPositionsFromCoordinates(allSolutionsCoordinates[i]))
        }

        if (isSolutionValid(solutionsPositions)) {
            solutions.add(Solution(lastPositionsSelection))
        }

        setSolutionItems()

        if (solutions.size == translation.locations.size) {
            view.stopListeningTouchEvents()
            view.showNextButton()
        }
    }

    private fun isSolutionAlreadyFound(): Boolean {
        val size = solutions.size
        return (0 until size).any { solutions[it].positions == lastPositionsSelection }
    }

    private fun isSolutionValid(expectedSolutions: List<List<Int>>): Boolean {
        val size = expectedSolutions.size
        return (0 until size).any { expectedSolutions[it] == lastPositionsSelection }
    }

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

    private fun setSelectedItems(positions: List<Int>) {
        lastPositionsSelection.clear()
        lastPositionsSelection.addAll(positions)
        view.setSelectedItems(positions)
    }

    private fun setSolutionItems() {
        val solutionItems = arrayListOf<Int>()
        val solutionSize = solutions.size
        for (i in 0 until solutionSize) {
            solutionItems.addAll(solutions[i].positions)
        }
        view.setSolutionItems(solutionItems)
    }

}