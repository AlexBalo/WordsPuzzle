package com.balocco.words.home.translations.usecases

import android.support.annotation.DrawableRes
import com.balocco.words.R
import javax.inject.Inject

class FlagSelectorUseCase @Inject constructor() {

    @DrawableRes
    fun getFlagResource(key: String): Int =
        when (key) {
            "en" -> R.drawable.ic_flag_en
            "es" -> R.drawable.ic_flag_es
            else -> R.drawable.ic_flag_en
        }
}