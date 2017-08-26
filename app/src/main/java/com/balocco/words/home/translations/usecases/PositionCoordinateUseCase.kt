package com.balocco.words.home.translations.usecases

import com.balocco.words.data.models.WordCoordinate
import javax.inject.Inject

class PositionCoordinateUseCase @Inject constructor(
) {

    fun coordinateFromPosition(gridSize: Int, position: Int): WordCoordinate {
        val x = position / gridSize
        val y = position % gridSize
        return WordCoordinate(x, y)
    }

    fun positionsFromCoordinates(gridSize: Int, coordinates: List<WordCoordinate>): List<Int> {
        val size = coordinates.size
        val positions = arrayListOf<Int>()
        for (i in 0 until size) {
            positions.add(positionFromCoordinate(gridSize, coordinates[i]))
        }
        return positions
    }

    private fun positionFromCoordinate(gridSize: Int, coordinate: WordCoordinate): Int =
            (coordinate.x * gridSize) + coordinate.y

}