package milu.kiriu2010.rss

import android.util.Log
import milu.kiriu2010.file.xml.MyXMLParse
import org.xml.sax.SAXException
import java.io.IOException
import javax.xml.parsers.ParserConfigurationException

// =======================================================
// XML文字列を元に、Rssオブジェクトを生成する
// -------------------------------------------------------
// 2020.05.30 どのオブジェクトを生成するべきか判断できない場合、
//            nullではなく、Exceptionを返却するようにした
// =======================================================
object MyRssParseFactory {
    @Throws(ParserConfigurationException::class, IOException::class, SAXException::class,Exception::class)
    fun createInstance( strXML: String ): MyRssParseRssAbs {
        val myXMLParse = MyXMLParse()
        val xmlDoc = myXMLParse.str2doc(strXML)
        // ルート要素
        val rootNode = xmlDoc.documentElement
        // ルート要素の属性
        val attrMap = mutableMapOf<String,String>()
        for ( i in 0 until rootNode.attributes.length ) {
            val attr = rootNode.attributes.item(i)
            Log.d( javaClass.simpleName, "rootAttr[$i][${attr.nodeName}][${attr.nodeValue}]")
            attrMap.put( attr.nodeName, attr.nodeValue )
        }

        val myRssParseAbs =
            /// ルート要素のタグ名によって、処理を振り分ける
            when ( rootNode.nodeName ) {
                // RSS2.0
                // RSS0.91
                "rss" -> {
                    analyzeRss( attrMap )
                }
                // RSS1.0
                "rdf:RDF" ->{
                    MyRssParseRss1M0()
                }
                else ->{
                    throw Exception("unknown nodeName({${rootNode.nodeName}})")
                }
            }

        myRssParseAbs.myXMLParse = myXMLParse
        myRssParseAbs.xmlRoot = xmlDoc

        return myRssParseAbs
    }

    @Throws(java.lang.Exception::class)
    private fun analyzeRss( attrMap: MutableMap<String,String> ): MyRssParseRssAbs {
        val version: String? = attrMap["version"]
        val xmlnsDC: String? = attrMap["xmlns:dc"]

        val myRssParseAbs =
            when ( version ) {
                "2.0" -> {
                    when ( xmlnsDC ) {
                        "http://purl.org/dc/elements/1.1/" -> MyRssParseRss2M0N11()
                        else -> MyRssParseRss2M0()
                    }
                }
                "0.91" -> MyRssParseRss0M91()
                else -> throw Exception("unknown RSS version[{$version}]")
            }

        return myRssParseAbs
    }
}