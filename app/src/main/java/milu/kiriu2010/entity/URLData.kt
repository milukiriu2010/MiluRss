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
            urlLst.add( URLData( 101, 1,
                    "痛いニュース(ﾉ∀`)",
                    URL("http://blog.livedoor.jp/dqnplus/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 102, 1,
                    "【2ch】ニュー速クオリティ",
                    URL("http://news4vip.livedoor.biz/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 103, 1,
                    "ハムスター速報",
                    URL("http://hamusoku.com/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 104, 1,
                    "ニュー速VIPブログ(`･ω･´)",
                    URL("http://blog.livedoor.jp/insidears/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 105, 1,
                    "アルファルファモザイク",
                    URL("http://alfalfalfa.com/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 106, 1,
                    "コピペ情報局",
                    URL("http://news.2chblog.jp/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 107, 1,
                    "watch@2チャンネル",
                    URL("http://watch2ch.2chblog.jp/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 108, 1,
                    "まとめたニュース",
                    URL("http://matometanews.com/index.rdf")) )
            // Atom 0.3
            // 2ch
            urlLst.add( URLData( 109, 1,
                    "ガハろぐNewsヽ(･ω･)/ｽﾞｺｰ",
                    URL("http://gahalog.2chblog.jp/atom.xml")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 110, 1,
                    "まめ速",
                    URL("http://mamesoku.com/index.rdf")) )
            // RSS 1.1
            // 2ch
            urlLst.add( URLData( 111, 1,
                    "VIPPERな俺",
                    URL("http://blog.livedoor.jp/news23vip/atom.xml")) )
            // Atom
            // 2ch
            urlLst.add( URLData( 112, 1,
                    "妹はVIPPER",
                    URL("http://vipsister23.com/index.rdf")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 113, 1,
                    "働くモノニュース",
                    URL("http://workingnews.blog117.fc2.com/?xml")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 114, 1,
                    "キニ速",
                    URL("http://blog.livedoor.jp/kinisoku/index.rdf")) )
            // Atom
            // 2ch
            urlLst.add( URLData( 115, 1,
                    "暇人＼(^o^)／速報",
                    URL("http://himasoku.com/atom.xml")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 116, 1,
                    "すくいぬ",
                    URL("http://suiseisekisuisui.blog107.fc2.com/?xml")) )
            // Atom
            // 2ch
            urlLst.add( URLData( 117, 1,
                    "ライフハックちゃんねる弐式",
                    URL("http://lifehack2ch.livedoor.biz/atom.xml")) )
            // Atom
            // 2ch
            urlLst.add( URLData( 118, 1,
                    "哲学ニュースnwk",
                    URL("http://blog.livedoor.jp/nwknews/atom.xml")) )
            // RSS 1.0
            // 2ch
            urlLst.add( URLData( 118, 1,
                    "ラジック",
                    URL("http://rajic.2chblog.jp/index.rdf")) )
            // Atom
            // 2ch
            urlLst.add( URLData( 119, 1,
                    "(*ﾟ∀ﾟ)ゞカガクニュース隊",
                    URL("http://www.scienceplus2ch.com/atom.xml")) )
            // Atom
            // 2ch
            urlLst.add( URLData( 120, 1,
                    "カオスちゃんねる",
                    URL("http://chaos2ch.com/atom.xml")) )
            // RSS 2.0+1.0
            // 豆知識
            urlLst.add( URLData(201,2,
                    "ライフハッカー",
                    URL("http://feeds.lifehacker.jp/rss/lifehacker/index.xml")) )
            // 上のURLだと301が返される
            // こっちが現在のURL
            //urlLst.add( URLData(5,2, "ライフハッカー", URL("https://www.lifehacker.jp/feed/index.xml")) )
            // RSS 2.0+1.0
            // 豆知識
            urlLst.add( URLData( 202,2,
                    "ロケットニュース",
                    URL("http://feeds.rocketnews24.com/rocketnews24")) )

            // ------------------------------------------------------------------------------------
            // RSS 2.0
            // ニュース
            urlLst.add( URLData( 301,3,
                    "Yahoo(主要)",
                    URL("https://news.yahoo.co.jp/pickup/rss.xml")) )
            // RSS 2.0
            // ニュース
            urlLst.add( URLData( 302,3,
                    "NHKニュース",
                    URL("https://www.nhk.or.jp/rss/news/cat0.xml")) )
            // RSS 1.0
            // ニュース
            urlLst.add( URLData( 303,3,
                    "日経新聞",
                    URL("https://assets.wor.jp/rss/rdf/nikkei/news.rdf")) )
            // RSS 2.0
            // ニュース
            urlLst.add( URLData( 311,3,
                    "CNN",
                    URL("http://rss.cnn.com/rss/edition.rss")) )
            // RSS 2.0
            // ニュース
            urlLst.add( URLData( 312,3,
                    "Wall Street Journal",
                    URL("https://feeds.a.dj.com/rss/RSSWorldNews.xml")) )
            // RSS 2.0
            // ニュース
            urlLst.add( URLData( 321,3,
                    "inquirer.net",
                    URL("https://www.inquirer.net/fullfeed")) )
            // RSS 2.0
            // ニュース
            urlLst.add( URLData( 331,3,
                    "Le Monde - La une International",
                    URL("https://www.lemonde.fr/international/rss_full.xml")) )
            // RSS 2.0
            // ニュース
            urlLst.add( URLData( 341,3,
                    "EL PAÍS - Internacional",
                    URL("https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/section/internacional/portada")) )
            // RSS 2.0
            // ニュース
            urlLst.add( URLData( 351,3,
                    "Ведомости - Все новости",
                    URL("https://www.vedomosti.ru/rss/news")) )
            // RSS 2.0
            // ニュース
            urlLst.add( URLData( 361,3,
                    "الشرق الأوسط - أخبار",
                    URL("https://aawsat.com/feed")) )

            // ------------------------------------------------------------------------------------
            // RSS 2.0
            // 天気
            urlLst.add( URLData( 401,4,
                    "Yahoo(東京)",
                    URL("https://rss-weather.yahoo.co.jp/rss/days/4410.xml")) )
            // RSS 2.0
            // 天気
            urlLst.add( URLData( 411,4,
                    "Manila, AR Weather",
                    URL("http://www.rssweather.com/zipcode/72442/rss.php")) )
            // RSS 2.0+1.0
            // 天気
            urlLst.add( URLData( 431,4,
                    "BBC(Manchester)",
                    URL("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643123")) )

            // ------------------------------------------------------------------------------------
            // RSS 2.0
            // IT
            urlLst.add( URLData( 501,5,
                    "ビジネスIT+IT HotTopics",
                    URL("https://www.sbbit.jp/rss/HotTopics.rss")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 502,5,
                    "＠IT Smart & Socialフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_smart.xml")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 503,5,
                    "＠IT HTML5 + UXフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_ux.xml")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 504, 5,
                    "＠IT Coding Edgeフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_coding.xml")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 505, 5,
                    "＠IT Java Agileフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_java.xml")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 506, 5,
                    "＠IT Database Expertフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_db.xml")) )
            // RSS 2.0
            // IT
            urlLst.add( URLData( 507, 5,
                    "＠IT Linux＆OSSフォーラム 最新記事一覧",
                    URL("https://rss.itmedia.co.jp/rss/2.0/ait_linux.xml")) )
            // RSS 2.0+1.0
            // IT
            urlLst.add( URLData( 508, 5,
                    "GIGAZINE",
                    URL("https://gigazine.net/news/rss_2.0/")) )


            // ------------------------------------------------------------------------------------
            // Atom
            // プログラミング
            urlLst.add( URLData( 601, 6,
                    "Qiita - Kotlin",
                    URL("https://qiita.com/tags/kotlin/feed")) )
            // Atom
            // プログラミング
            urlLst.add( URLData( 602, 6,
                    "Qiita - Android",
                    URL("https://qiita.com/tags/android/feed")) )
            // Atom
            // プログラミング
            urlLst.add( URLData( 603, 6,
                    "Qiita - GLSL",
                    URL("https://qiita.com/tags/glsl/feed")) )
            // Atom
            // プログラミング
            urlLst.add( URLData( 604, 6,
                    "Qiita - Java",
                    URL("https://qiita.com/tags/java/feed")) )

            // ------------------------------------------------------------------------------------
            // Atom
            // スポーツ
            urlLst.add( URLData( 701, 7,
                    "日刊スポーツ - 阪神",
                    URL("https://www.nikkansports.com/rss/baseball/professional/atom/tigers.xml")) )
            urlLst.add( URLData( 702, 7,
                    "日刊スポーツ - プロ野球",
                    URL("https://www.nikkansports.com/baseball/professional/atom.xml")) )
            urlLst.add( URLData( 703, 7,
                    "日刊スポーツ - MLB",
                    URL("https://www.nikkansports.com/baseball/mlb/atom.xml")) )
            urlLst.add( URLData( 704, 7,
                    "日刊スポーツ - サッカー",
                    URL("https://www.nikkansports.com/soccer/atom.xml")) )
            urlLst.add( URLData( 705, 7,
                    "日刊スポーツ - 海外サッカー",
                    URL("https://www.nikkansports.com/soccer/world/atom.xml")) )


            // 一致するジャンルのRssコンテンツ一覧を返すようフィルターをかけている
            return urlLst.filter { urlData -> urlData.genreId.equals(genreData.id) }.toMutableList()

            //return urlLst
        }
    }

}
