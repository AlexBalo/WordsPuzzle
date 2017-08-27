package com.balocco.words.home.translations.usecases

import android.support.annotation.DimenRes
import com.balocco.words.R
import javax.inject.Inject

class CharacterTextSizeUseCase @Inject constructor(
) {

    @DimenRes
    fun getTextSizeBasedOnGrid(gridSize: Int): Int =
            when (gridSize) {
                in 0..5 -> R.dimen.two_and_three_quarter_text_unit
                6 -> R.dimen.two_and_half_text_unit
                7 -> R.dimen.two_and_quarter_text_unit
                8 -> R.dimen.two_text_unit
                else -> R.dimen.one_and_three_quarter_text_unit
            }
}