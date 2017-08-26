package com.balocco.words.data.models

import android.os.Parcel
import android.os.Parcelable

data class Translation(
        val sourceLanguage: String,
        val targetLanguage: String,
        val word: String,
        val gridSize: Int,
        val characterList: List<String>,
        val locations: List<WordLocation>
) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.createStringArrayList(),
            ArrayList<WordLocation>().apply {
                source.readList(this, WordLocation::class.java.classLoader)
            }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(sourceLanguage)
        writeString(targetLanguage)
        writeString(word)
        writeInt(gridSize)
        writeStringList(characterList)
        writeList(locations)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Translation> = object : Parcelable.Creator<Translation> {
            override fun createFromParcel(source: Parcel): Translation = Translation(source)
            override fun newArray(size: Int): Array<Translation?> = arrayOfNulls(size)
        }
    }
}