package com.balocco.words.data.local

import com.balocco.words.data.models.Translation
import javax.inject.Inject

class TranslationsStore @Inject constructor() {

    private var translations: ArrayList<Translation> = arrayListOf()

    fun addTranslation(translation: Translation) {
        translations.add(translation)
    }

    fun hasTranslations(): Boolean = !translations.isEmpty()

    fun clearTranslations() = translations.clear()

    fun fetchTranslationWithIndex(index: Int): Translation? =
            if (index < translations.size) translations[index] else null

}