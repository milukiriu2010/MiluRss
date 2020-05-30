package milu.kiriu2010.entity

import android.os.Parcel
import android.os.Parcelable
import java.net.URL

// RSSのジャンル
data class GenreData(
        var id: Int = -1,
        var genre: String = "",
        var pos: Int = -1 ): Parcelable {

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

        // ジャンル一覧
        fun loadGenreData(): MutableList<GenreData> {
            val genreLst: MutableList<GenreData> = mutableListOf<GenreData>()

            genreLst.add( GenreData( 1,"2ch", 1 ))
            genreLst.add( GenreData( 2,"豆知識", 2 ))
            genreLst.add( GenreData( 3,"ニュース", 3))
            genreLst.add( GenreData( 4,"天気", 4))
            genreLst.add( GenreData( 5,"IT", 5 ) )
            genreLst.add( GenreData( 6,"スポーツ", 6 ) )

            return genreLst
        }

    }

}