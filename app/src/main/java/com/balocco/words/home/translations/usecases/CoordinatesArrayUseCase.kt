package com.balocco.words.home.translations.usecases

import com.balocco.words.data.models.WordCoordinate
import javax.inject.Inject

class CoordinatesArrayUseCase @Inject constructor(
) {

    fun calculateCoordinatesOnSameRow(
            coordinateHead: WordCoordinate,
            coordinateTail: WordCoordinate
    ): List<WordCoordinate> {
        val coordinateList = arrayListOf(coordinateHead)
        if (coordinateHead.x != coordinateTail.x) {
            return coordinateList
        }

        var indexY = coordinateHead.y + 1
        while (indexY <= coordinateTail.y) {
            coordinateList.add(WordCoordinate(coordinateHead.x, indexY))
            indexY++
        }
        return coordinateList
    }

    fun calculateCoordinatesOnSameColumn(
            coordinateHead: WordCoordinate,
            coordinateTail: WordCoordinate
    ): List<WordCoordinate> {
        val coordinateList = arrayListOf(coordinateHead)
        if (coordinateHead.y != coordinateTail.y) {
            return coordinateList
        }

        var indexX = coordinateHead.x + 1
        while (indexX <= coordinateTail.x) {
            coordinateList.add(WordCoordinate(indexX, coordinateHead.y))
            indexX++
        }
        return coordinateList
    }

    fun calculateCoordinatesDiagonally(
            coordinateHead: WordCoordinate,
            coordinateTail: WordCoordinate
    ): List<WordCoordinate> {
        val coordinateList = arrayListOf(coordinateHead)
        var indexX = coordinateHead.x + 1
        var indexY = coordinateHead.y + 1
        while (indexX <= coordinateTail.x && indexY <= coordinateTail.y) {
            coordinateList.add(WordCoordinate(indexX, indexY))
            indexX++
            indexY++
        }
        return coordinateList
    }

}