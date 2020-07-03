package milu.kiriu2010.rss

import android.util.Log
import milu.kiriu2010.entity.Article
import milu.kiriu2010.entity.Rss
import milu.kiriu2010.tool.MyTool
import java.util.*

class MyRssParseRssAtom: MyRssParseRssAbs() {

    init {
        ver = "atom"
    }

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
        //val titleNode = myXMLParse.searchNode( xmlRoot, "/*[name()='feed']/title/text()")
        val titleNode = myXMLParse.searchNode( xmlRoot, "/feed/title/text()")
        Log.d( javaClass.simpleName, "title[${titleNode?.nodeValue}]")

        // -------------------------------------------------------
        // RSSのpubDateを取得
        // -------------------------------------------------------
        val pubDateNode = myXMLParse.searchNode( xmlRoot, "/feed/modified/text()")

        Log.d( javaClass.simpleName, "pubDate[$pubDateNode.nodeValue]")
        // 2020-06-08T18:01:08Z
        // 2020-06-11 08:35:30Z
        val pubDate = pubDateNode?.let {
            MyTool.rfc3339date(it.nodeValue)
        } ?: Date()

        // -------------------------------------------------------
        // RSSフィード内の記事の一覧
        // -------------------------------------------------------
        val articles = mutableListOf<Article>()

        // -------------------------------------------------------
        // RSSの記事(<entry>要素)をすべて取得
        // -------------------------------------------------------
        val itemNodeList = myXMLParse.searchNodeList( xmlRoot, "/feed//entry" )

        // -------------------------------------------------------
        // RSSの記事(<entry>要素)ごとにループ
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
            val itemLinkNode = myXMLParse.searchNode( itemNode, "./link" )
            // link要素の属性
            val linkAttrMap = mutableMapOf<String,String>()
            itemLinkNode?.let {
                for ( j in 0 until it.attributes.length ) {
                    val attr = it.attributes.item(j)
                    Log.d( javaClass.simpleName, "linkAttr[$j][${attr.nodeName}][${attr.nodeValue}]")
                    linkAttrMap.put( attr.nodeName, attr.nodeValue )
                }
            }
            // href属性にコンテンツURLが設定されている
            // <link rel="alternate" type="text/html" href="http://chaos2ch.com/archives/5183183.html"/>
            val link = linkAttrMap["href"] ?: "不明"

            // -------------------------------------------------------
            // 記事のpubDateを取得
            // -------------------------------------------------------
            val itemPubDateNode = myXMLParse.searchNode( itemNode, "./issued/text()" ) ?:
                myXMLParse.searchNode( itemNode, "./updated/text()" ) ?:
                myXMLParse.searchNode( itemNode, "./published/text()" )
            Log.d( javaClass.simpleName, "=============================================")
            Log.d( javaClass.simpleName, "itemTitle[${itemTitleNode?.nodeValue}]")
            Log.d( javaClass.simpleName, "itemLink[${itemLinkNode?.nodeValue}]")
            Log.d( javaClass.simpleName, "itemPubDate[${itemPubDateNode?.nodeValue}]")
            Log.d( javaClass.simpleName, "=============================================")

            val article = Article(
                    itemTitleNode!!.nodeValue,
                    link,
                    MyTool.rfc3339date(itemPubDateNode?.nodeValue!!)
            )

            articles.add(article)
        }

        // RSSオブジェクトを生成
        val rss = Rss(
                ver,
                titleNode!!.nodeValue,
                pubDate,
                articles
        )

        return rss
    }
}
