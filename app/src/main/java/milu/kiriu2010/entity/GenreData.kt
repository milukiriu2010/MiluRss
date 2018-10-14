package milu.kiriu2010.entity

import android.os.Parcel
import android.os.Parcelable

data class GenreData(var id: Int = -1, var genre: String = "", var pos: Int = -1 ): Parcelable {

    constructor(parcel: Parcel) : this(
            // id
            parcel.readInt(),
            // genre
            parcel.readString() ?: "",
            // pos
            parcel.readInt()) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeInt(id)
            it.writeString( genre )
            it.writeValue( pos )
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<GenreData> {
        override fun createFromParcel(parcel: Parcel): GenreData {
            return GenreData(parcel)
        }

        override fun newArray(size: Int): Array<GenreData?> {
            return arrayOfNulls(size)
        }
    }

}