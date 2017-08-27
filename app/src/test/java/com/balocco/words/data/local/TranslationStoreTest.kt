package com.balocco.words.data.local

import com.balocco.words.data.models.Translation
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TranslationStoreTest {

    @Mock private lateinit var translation: Translation
    @Mock private lateinit var secondTranslation: Translation

    private lateinit var store: TranslationsStore

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        store = TranslationsStore()
    }

    @Test fun `When has translation, returns false by default`() {
        val hasTranslations = store.hasTranslations()

        assertThat(hasTranslations).isFalse()
    }

    @Test fun `When has translation after adding one, returns true`() {
        store.addTranslation(translation)

        val hasTranslations = store.hasTranslations()

        assertThat(hasTranslations).isTrue()
    }

    @Test fun `When get translation count, returns expected value`() {
        store.addTranslation(translation)
        store.addTranslation(translation)

        val count = store.getTranslationCount()

        assertThat(count).isEqualTo(2)
    }

    @Test fun `When clear translation, return zero as translation count`() {
        store.addTranslation(translation)
        store.addTranslation(translation)

        store.clearTranslations()
        val count = store.getTranslationCount()

        assertThat(count).isEqualTo(0)
    }

    @Test fun `When fetch translation with index, returns expected translation`() {
        store.addTranslation(translation)
        store.addTranslation(secondTranslation)

        val translation = store.fetchTranslationWithIndex(1)

        assertThat(translation).isEqualTo(secondTranslation)
    }

    @Test fun `When fetch translation with index out of bounds, returns null translation`() {
        store.addTranslation(translation)
        store.addTranslation(secondTranslation)

        val translation = store.fetchTranslationWithIndex(3)

        assertThat(translation).isNull()
    }
}