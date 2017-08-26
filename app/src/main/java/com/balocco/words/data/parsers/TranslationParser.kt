package com.balocco.words.data.parsers

import com.balocco.words.data.models.Translation
import com.balocco.words.data.models.WordCoordinate
import com.balocco.words.data.models.WordLocation
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject


private const val COMMA = ","
private const val SOURCE_LANGUAGE = "source_language"
private const val TARGET_LANGUAGE = "target_language"
private const val WORD = "word"
private const val CHARACTER_GRID = "character_grid"
private const val WORD_LOCATIONS = "word_locations"

class TranslationParser @Inject constructor() {

    fun parseTranslation(translationString: String): Translation {
        val jsonObject = JSONObject(translationString)

        val sourceLanguage = jsonObject.getString(SOURCE_LANGUAGE)

        val word = jsonObject.getString(WORD)

        val characterGrid = jsonObject.getJSONArray(CHARACTER_GRID)
        val gridSize = characterGrid.length()
        val characters = createCharacterList(characterGrid, gridSize)

        val wordLocation = jsonObject.getJSONObject(WORD_LOCATIONS)
        val locations = createLocationList(wordLocation)

        val targetLanguage = jsonObject.getString(TARGET_LANGUAGE)

        return Translation(
                sourceLanguage,
                targetLanguage,
                word,
                gridSize,
                characters,
                locations)
    }

    private fun createCharacterList(characterGrid: JSONArray, gridSize: Int): List<String> {
        val characters = arrayListOf<String>()
        var row: JSONArray?
        for (i in 0 until gridSize) {
            row = characterGrid.getJSONArray(i)
            for (j in 0 until gridSize) {
                characters.add(row.getString(j))
            }
        }
        return characters
    }

    private fun createLocationList(wordLocations: JSONObject): List<WordLocation> {
        val wordLocationsList = arrayListOf<WordLocation>()

        var key: String?
        var value: String?
        var wordCoordinates: List<WordCoordinate>
        val iterator = wordLocations.keys()
        while (iterator.hasNext()) {
            key = iterator.next()
            value = wordLocations.getString(key)
            val coordinates = key.split(COMMA)
            wordCoordinates = arrayListOf()
            for (j in 0 until coordinates.size step 2) {
                val x = coordinates[j].toInt()
                val y = coordinates[j + 1].toInt()
                wordCoordinates.add(WordCoordinate(x, y))
            }
            wordLocationsList.add(WordLocation(wordCoordinates, value))
        }
        return wordLocationsList
    }
}