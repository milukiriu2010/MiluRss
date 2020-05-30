package milu.kiriu2010.entity

import android.os.Parcel
import android.os.Parcelable
import java.util.*


// RSSの各記事を表すデータクラス
data class Article( val title: String, val link: String, val pubDate: Date): Parcelable {
    constructor(parcel: Parcel) : this(
            // title
            parcel.readString() ?: "",
            // link
            parcel.readString() ?: "",
            // pubDate
            Date(parcel.readLong())) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeString(title)
            it.writeString(link)
            it.writeLong(pubDate.time)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}
