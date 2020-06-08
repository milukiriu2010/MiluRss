package milu.kiriu2010.entity

import android.os.Parcel
import android.os.Parcelable
import java.net.URL

/*
http://www.parcelabler.com/
    protected URLData(Parcel in) {
        title = in.readString();
        url = (URL) in.readValue(URL.class.getClassLoader());
    }
 */
// 取得するRssのコンテンツ情報
// ・タイトル
// ・URL
data class URLData(
        val id: Int,
        // GenreDataのidに対応する
        val genreId: Int,
        val title: String,
        val url: URL ): Parcelable {
    constructor( parcel: Parcel ): this(
            // id
            parcel.readInt(),
            // genreId
            parcel.readInt(),
            // title
            parcel.readString() ?: "",
            // URL
            parcel.readValue(URL::class.java.classLoader) as URL
    )

    override fun writeToParcel( dest: Parcel?, flag: Int) {
        dest?.let {
            it.writeInt(id)
            it.writeInt( genreId )
            it.writeString( title )
            it.writeValue( url )
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<URLData> {
        override fun createFromParcel(parcel: Parcel): URLData {
            return URLData(parcel)
        }

        override fun newArray(size: Int): Array<URLData?> {
            return arrayOfNulls(size)
        }

        // ジャンルに対応するURL一覧を生成する
        fun loadURLData(genreData: GenreData): MutableList<URLData> {
            val urlLst: MutableList<URLData> = mutableListOf()

            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 1, 1,
                    "痛いニュース(ﾉ∀`)",
                    URL("http://blog.livedoor.jp/dqnplus/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 2, 1,
                    "【2ch】ニュー速クオリティ",
                    URL("http://news4vip.livedoor.biz/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 3, 1,
                    "ハムスター速報",
                    URL("http://hamusoku.com/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 4, 1,
                    "ニュー速VIPブログ(`･ω･´)",
                    URL("http://blog.livedoor.jp/insidears/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 5, 1,
                    "アルファルファモザイク",
                    URL("http://alfalfalfa.com/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 6, 1,
                    "コピペ情報局",
                    URL("http://news.2chblog.jp/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 7, 1,
                    "watch@2チャンネル",
                    URL("http://watch2ch.2chblog.jp/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 8, 1,
                    "まとめたニュース",
                    URL("http://matometanews.com/index.rdf")) )
            // RSS 1.1
            // 2ch
            urlLst.add( URLData( 9, 1,
                    "ガハろぐNewsヽ(･ω･)/ｽﾞｺｰ",
                    URL("http://gahalog.2chblog.jp/atom.xml")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 10, 1,
                    "まめ速",
                    URL("http://mamesoku.com/index.rdf")) )
            // RSS 1.1
            // 2ch
            urlLst.add( URLData( 11, 1,
                    "VIPPERな俺",
                    URL("http://blog.livedoor.jp/news23vip/atom.xml")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 12, 1,
                    "妹はVIPPER",
                    URL("http://vipsister23.com/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 13, 1,
                    "働くモノニュース",
                    URL("http://workingnews.blog117.fc2.com/?xml")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 14, 1,
                    "キニ速",
                    URL("http://blog.livedoor.jp/kinisoku/index.rdf")) )
            // RSS 1.1
            // 2ch
            urlLst.add( URLData( 15, 1,
                    "暇人＼(^o^)／速報",
                    URL("http://himasoku.com/atom.xml")) )
            // RSS 1.6
            // 2ch
            urlLst.add( URLData( 16, 1,
                    "すくいぬ",
                    URL("http://suiseisekisuisui.blog107.fc2.com/?xml")) )
            // RSS 1.1
            // 2ch
            urlLst.add( URLData( 17, 1,
                    "ライフハックちゃんねる弐式",
                    URL("http://lifehack2ch.livedoor.biz/atom.xml")) )
            // RSS 1.1
            // 2ch
            urlLst.add( URLData( 18, 1,
                    "哲学ニュースnwk",
                    URL("http://blog.livedoor.jp/nwknews/atom.xml")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 18, 1,
                    "ラジック",
                    URL("http://rajic.2chblog.jp/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 19, 1,
                    "(*ﾟ∀ﾟ)ゞカガクニュース隊",
                    URL("http://www.scienceplus2ch.com/atom.xml")) )
            // RSS 1.1
            // 2ch
            urlLst.add( URLData( 20, 1,
                    "カオスちゃんねる",
                    URL("http://chaos2ch.com/atom.xml")) )
            // RSS 2.0
            // 豆知識
            urlLst.add( URLData(101,2,
                    "ライフハッカー",
                    URL("http://feeds.lifehacker.jp/rss/lifehacker/index.xml")) )
            // 上のURLだと301が返される
            // こっちが現在のURL
            //urlLst.add( URLData(5,2, "ライフハッカー", URL("https://www.lifehacker.jp/feed/index.xml")) )
            // RSS 2.0
            // 豆知識
            urlLst.add( URLData( 102,2,
                    "ロケットニュース",
                    URL("http://feeds.rocketnews24.com/rocketnews24")) )
            // RSS 2.0
            // ニュース
            urlLst.add( URLData( 201,3,
                    "Yahoo(主要)",
                    URL("https://news.yahoo.co.jp/pickup/rss.xml")) )
            // RSS 2.0
            // 天気
            urlLst.add( URLData( 301,4,
                    "Yahoo(東京)",
                    URL("https://rss-weather.yahoo.co.jp/rss/days/4410.xml")) )
            // RSS 2.0
            // 天気
            urlLst.add( URLData( 302,4,
                    "BBC(Manchester)",
                    URL("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643123")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 401,5,
                    "ビジネスIT+IT HotTopics",
                    URL("https://www.sbbit.jp/rss/HotTopics.rss")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 402,5,
                    "＠IT Smart & Socialフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_smart.xml")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 403,5,
                    "＠IT HTML5 + UXフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_ux.xml")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 403, 5,
                    "＠IT Coding Edgeフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_coding.xml")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 404, 5,
                    "＠IT Java Agileフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_java.xml")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 405, 5,
                    "＠IT Database Expertフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_db.xml")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 406, 5,
                    "＠IT Linux＆OSSフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_linux.xml")) )
            // RSS 2.0+1.1?
            // IT
            urlLst.add( URLData( 407, 5,
                    "GIGAZINE",
                    URL("https://gigazine.net/news/rss_2.0/")) )

            // 一致するジャンルのRssコンテンツ一覧を返すようフィルターをかけている
            return urlLst.filter { urlData -> urlData.genreId.equals(genreData.id) }.toMutableList()

            //return urlLst
        }
    }

}
