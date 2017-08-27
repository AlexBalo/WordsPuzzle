package com.balocco.words.data.parsers

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

private const val SOURCE_LANGUAGE = "sourceLanguage"
private const val TARGET_LANGUAGE = "targetLanguage"
private const val WORD = "word"
private const val CHAR1 = "char1"
private const val CHAR2 = "char2"
private const val CHAR3 = "char3"
private const val CHAR4 = "char4"
private const val LOCATION_X = 1
private const val LOCATION_Y = 0
private const val LOCATION_WORD = "n"

private const val TRANSLATION_ITEM = "{" +
        "\"source_language\": " + SOURCE_LANGUAGE + ", " +
        "\"word\": " + WORD + ", " +
        "\"character_grid\": [[" + CHAR1 + ", " + CHAR2 + "], " + "[" + CHAR3 + ", " + CHAR4 + "]], " +
        "\"word_locations\": {\"0,1\": " + LOCATION_WORD + "}, " +
        "\"target_language\": " + TARGET_LANGUAGE +
        "}\n"

@RunWith(RobolectricTestRunner::class)
class TranslationParserTest {

    private lateinit var parser: TranslationParser

    @Before fun setUp() {
        parser = TranslationParser()
    }

    @Test fun `When parse translation, returns expected source language`() {
        val translation = parser.parseTranslation(TRANSLATION_ITEM)

        assertThat(translation.sourceLanguage).isEqualTo(SOURCE_LANGUAGE)
    }

    @Test fun `When parse translation, returns expected target language`() {
        val translation = parser.parseTranslation(TRANSLATION_ITEM)

        assertThat(translation.targetLanguage).isEqualTo(TARGET_LANGUAGE)
    }

    @Test fun `When parse translation, returns expected word`() {
        val translation = parser.parseTranslation(TRANSLATION_ITEM)

        assertThat(translation.word).isEqualTo(WORD)
    }

    @Test fun `When parse translation, returns expected grid size`() {
        val translation = parser.parseTranslation(TRANSLATION_ITEM)

        assertThat(translation.gridSize).isEqualTo(2)
    }

    @Test fun `When parse translation, returns expected word locations`() {
        val translation = parser.parseTranslation(TRANSLATION_ITEM)

        val location = translation.locations[0]
        val coordinates = location.coordinates[0]
        assertThat(translation.locations.size).isEqualTo(1)
        assertThat(location.coordinates.size).isEqualTo(1)
        assertThat(location.word).isEqualTo(LOCATION_WORD)
        assertThat(coordinates.x).isEqualTo(LOCATION_X)
        assertThat(coordinates.y).isEqualTo(LOCATION_Y)
    }
}