package com.balocco.words.data.models

import android.os.Parcel
import android.os.Parcelable

data class WordLocation(
        val coordinates: List<WordCoordinate>,
        val word: String
) : Parcelable {

    constructor(source: Parcel) : this(
            ArrayList<WordCoordinate>().apply {
                source.readList(this, WordCoordinate::class.java.classLoader)
            },
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeList(coordinates)
        writeString(word)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<WordLocation> = object : Parcelable.Creator<WordLocation> {
            override fun createFromParcel(source: Parcel): WordLocation = WordLocation(source)
            override fun newArray(size: Int): Array<WordLocation?> = arrayOfNulls(size)
        }
    }
}