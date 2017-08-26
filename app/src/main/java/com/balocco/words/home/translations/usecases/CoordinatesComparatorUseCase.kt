package com.balocco.words.home.translations.usecases

import com.balocco.words.data.models.WordCoordinate
import javax.inject.Inject

class CoordinatesComparatorUseCase @Inject constructor(
) {

    fun isCoordinateAbovePivot(
            pivotCoordinate: WordCoordinate,
            coordinate: WordCoordinate
    ) = coordinate.y < pivotCoordinate.y

    fun isCoordinateLeftOfPivot(
            pivotCoordinate: WordCoordinate,
            coordinate: WordCoordinate
    ) = coordinate.x < pivotCoordinate.x

    fun isCoordinateRightOfPivotOnSameRow(
            pivotCoordinate: WordCoordinate,
            coordinate: WordCoordinate
    ) = coordinate.x == pivotCoordinate.x

    fun isCoordinateBelowPivotOnSameColumn(
            pivotCoordinate: WordCoordinate,
            coordinate: WordCoordinate
    ) = coordinate.y == pivotCoordinate.y
}