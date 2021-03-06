package milu.kiriu2010.entity

import android.os.Parcel
import android.os.Parcelable
import org.w3c.dom.NodeList
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

// RSSを表現するデータクラス
data class Rss(
        var ver: String = "",
        var title: String = "",
        var pubDate: Date = Date(),
        val articles: MutableList<Article> = mutableListOf()
): Parcelable {
    @Suppress("UNCHECKED_CAST")
    constructor(parcel: Parcel) : this(
            // ver
            parcel.readString() ?: "",
            // title
            parcel.readString()  ?: "",
            // pubDate
            Date(parcel.readLong()),
            // https://haruue.moe/blog/2017/12/22/Kotlin-and-Android-Parcelable/
            parcel.readArrayList(Article::class.java.classLoader) as MutableList<Article>) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeString(ver)
            it.writeString(title)
            it.writeLong(pubDate.time)
            it.writeList(articles as List<Article>)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Rss> {
        override fun createFromParcel(parcel: Parcel): Rss {
            return Rss(parcel)
        }

        override fun newArray(size: Int): Array<Rss?> {
            return arrayOfNulls(size)
        }

    }

}

// RSS2.0をパースしてRssオブジェクトに変換する
fun parseRss(stream: InputStream) : Rss {

    // XMLをDOMオブジェクトに変換する
    val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream)
    stream.close()

    // XPathを生成する
    val xPath = XPathFactory.newInstance().newXPath()

    // RSS2.0の日付書式である、RFC1123をDate型に変換するためのオブジェクト
    // Fri, 24 Aug 2018 07:10:00 +0900
    val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)

    // チャンネル内の<item>要素を全て取り出す
    val items = xPath.evaluate("/rss/channel//item", doc, XPathConstants.NODESET) as NodeList

    // RSSフィード内の記事の一覧
    val articles = arrayListOf<Article>()

    // <item>の要素ごとに繰り返す
    for (i in 0 until items.length) {
        val item = items.item(i)

        // Articleオブジェクトにまとめる
        val article = Article(
                title = xPath.evaluate("./title/text()", item),
                link  = xPath.evaluate("./link/text()", item),
                pubDate = formatter.parse(xPath.evaluate("./pubDate/text()", item))!!
        )

        articles.add(article)
    }

    // RSSオブジェクトにまとめて返す
    return Rss(title = xPath.evaluate("/rss/channel/title/text()", doc),
            pubDate = formatter.parse(xPath.evaluate("/rss/channel/pubDate/text()", doc))!!,
            articles = articles)
}

