package com.balocco.words.home.translations.viewmodels

import android.os.Parcel
import android.os.Parcelable

data class Solution(
        val positions: List<Int>
) : Parcelable {

    constructor(source: Parcel) : this(
            ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeList(positions)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Solution> = object : Parcelable.Creator<Solution> {
            override fun createFromParcel(source: Parcel): Solution = Solution(source)
            override fun newArray(size: Int): Array<Solution?> = arrayOfNulls(size)
        }
    }
}