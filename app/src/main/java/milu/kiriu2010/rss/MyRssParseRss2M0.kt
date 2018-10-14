package milu.kiriu2010.rss

import android.util.Log
import milu.kiriu2010.entity.Article
import milu.kiriu2010.entity.Rss
import org.w3c.dom.Node
import java.text.SimpleDateFormat
import java.util.*

// https://www.sbbit.jp/rss/HotTopics.rss
// rss2.0
class MyRssParseRss2M0: MyRssParseRssAbs() {
    override fun analyze(): Rss {
        // -------------------------------------------------------
        // DOMのルートタグ名を取得
        // -------------------------------------------------------
        val rootNode = xmlRoot.documentElement
        Log.d( javaClass.simpleName, "root[${rootNode.nodeName}][${rootNode.nodeValue}]")

        for ( i in 0 until rootNode.attributes.length ) {
            val attr = rootNode.attributes.item(i)
            Log.d( javaClass.simpleName, "rootAttr[$i][${attr.nodeName}][${attr.nodeValue}]")
        }


        // -------------------------------------------------------
        // RSSのtitleを取得
        // -------------------------------------------------------
        val titleNode = myXMLParse.searchNode( xmlRoot, "/rss/channel/title/text()")
        Log.d( javaClass.simpleName, "title[${titleNode?.nodeValue}]")

        // -------------------------------------------------------
        // RSSのpubDateを取得
        // -------------------------------------------------------
        var pubDateNode: Node?
        try {
            pubDateNode = myXMLParse.searchNode( xmlRoot, "/rss/channel/pubDate/text()")
            // Yahoo天気の場合、"pubDate"ではなく"lastBuildDate"になってる
            if ( pubDateNode == null ) {
                pubDateNode = myXMLParse.searchNode( xmlRoot, "/rss/channel/lastBuildDate/text()")
            }
        }
        catch ( ex: Exception ) {
            // https://rss-weather.yahoo.co.jp/rss/days/4410.xml
            // Yahoo天気の場合、"pubDate"ではなく"lastBuildDate"になってる
            // kotlin.TypeCastException: null cannot be cast to non-null type org.w3c.dom.Node
            pubDateNode = myXMLParse.searchNode( xmlRoot, "/rss/channel/lastBuildDate/text()")
        }

        Log.d( javaClass.simpleName, "pubDate[$pubDateNode.nodeValue]")
        // RSS2.0の日付書式である、RFC1123をDate型に変換するためのオブジェクト
        // Fri, 24 Aug 2018 07:10:00 +0900
        val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
        // RSSのpubDateをDate型に変換
        val pubDate = formatter.parse(pubDateNode?.nodeValue)

        // -------------------------------------------------------
        // RSSフィード内の記事の一覧
        // -------------------------------------------------------
        val articles = mutableListOf<Article>()

        // -------------------------------------------------------
        // RSSの記事(<item>要素)をすべて取得
        // -------------------------------------------------------
        val itemNodeList = myXMLParse.searchNodeList( xmlRoot, "/rss/channel//item" )

        // -------------------------------------------------------
        // RSSの記事(<item>要素)ごとにループ
        // -------------------------------------------------------
        for ( i in 0 until itemNodeList.length ) {
            val itemNode = itemNodeList.item(i)

            // -------------------------------------------------------
            // 記事のtitleを取得
            // -------------------------------------------------------
            val itemTitleNode = myXMLParse.searchNode( itemNode, "./title/text()" )
            // -------------------------------------------------------
            // 記事のlinkを取得
            // -------------------------------------------------------
            val itemLinkNode = myXMLParse.searchNode( itemNode, "./link/text()" )
            // -------------------------------------------------------
            // 記事のpubDateを取得
            // -------------------------------------------------------
            val itemPubDateNode = myXMLParse.searchNode( itemNode, "./pubDate/text()" )
            Log.d( javaClass.simpleName, "=============================================")
            Log.d( javaClass.simpleName, "itemTitle[${itemTitleNode?.nodeValue}]")
            Log.d( javaClass.simpleName, "itemLink[${itemLinkNode?.nodeValue}]")
            Log.d( javaClass.simpleName, "itemPubDate[${itemPubDateNode?.nodeValue}]")
            Log.d( javaClass.simpleName, "=============================================")

            val article = Article(
                    itemTitleNode!!.nodeValue,
                    itemLinkNode!!.nodeValue,
                    formatter.parse(itemPubDateNode?.nodeValue)
            )

            articles.add(article)
        }

        // RSSオブジェクトを生成
        val rss = Rss(
                titleNode!!.nodeValue,
                pubDate,
                articles
        )

        return rss
    }

}