package com.balocco.words.data.models

import android.os.Parcel
import android.os.Parcelable

data class WordCoordinate(
        val x: Int,
        val y: Int
) : Parcelable {

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(x)
        writeInt(y)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<WordCoordinate> = object : Parcelable.Creator<WordCoordinate> {
            override fun createFromParcel(source: Parcel): WordCoordinate = WordCoordinate(source)
            override fun newArray(size: Int): Array<WordCoordinate?> = arrayOfNulls(size)
        }
    }
}