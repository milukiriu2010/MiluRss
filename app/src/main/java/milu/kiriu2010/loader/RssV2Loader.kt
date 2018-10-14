package milu.kiriu2010.loader

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import android.util.Log
import milu.kiriu2010.entity.Rss
import milu.kiriu2010.entity.URLData
import milu.kiriu2010.netv2.MyURLConAbs
import milu.kiriu2010.netv2.MyURLConFactory
import milu.kiriu2010.rss.MyRssParseFactory
import java.net.URL


// RSSフィードをダウンロードしてRssオブジェクトを返すローダー
class RssV2Loader(context: Context, val urlData: URLData? ) : AsyncTaskLoader<AsyncResult<Rss>>(context) {

    private var cache : AsyncResult<Rss>? = null

    /*
    // このローダーがバックグラウンドで行う処理
    override fun loadInBackground(): AsyncResult<Rss>? {
        Log.d( javaClass.simpleName, "========================" )
        Log.d( javaClass.simpleName, urlData?.url?.toString() )

        // URLがない場合、何も処理せず終了する
        urlData?.url?.toString() ?: return null

        // Loader呼び出し元が受け取るデータ
        val asyncResult = AsyncResult<Rss>()

        // 接続＆データをGETする
        val urlConAbs = MyURLConFactory.createInstance(urlData.url,null)
        try {
            urlConAbs?.apply {
                openConnection()

                // 処理終了後、クローズする
                addSendHeader("Connection", "close")

                // ------------------------------------------
                // 接続＆GETする
                // ------------------------------------------
                doGet()

                // ----------------------------------------------
                // 通信が成功してれば、取得した文字列をRSS解析する
                // ----------------------------------------------
                if ( responseOK == MyURLConAbs.RESPONSE_OK.OK ) {
                    //val myRssParse = MyRssParse()
                    //val rss = myRssParse.str2rss(this.responseBuffer.toString())
                    val myRssParseAbs = MyRssParseFactory.createInstance(this.responseBuffer.toString())
                    if ( myRssParseAbs != null ) {
                        val rss = myRssParseAbs.analyze()
                        asyncResult.data = rss
                    }
                    else {
                        throw Exception("Unknown Format RSS")
                    }
                }
            }
        }
        catch ( ex: Exception ) {
            asyncResult.exception = ex
            ex.printStackTrace()
        }

        return asyncResult
    }
    */

    // このローダーがバックグラウンドで行う処理
    override fun loadInBackground(): AsyncResult<Rss>? {
        Log.d( javaClass.simpleName, "========================" )
        Log.d( javaClass.simpleName, urlData?.url?.toString() )

        // URLがない場合、何も処理せず終了する
        urlData?.url?.toString() ?: return null

        return accessPeer(urlData.url,null)
    }

    private fun accessPeer(url: URL, myURLConAbsCmp: MyURLConAbs? ): AsyncResult<Rss> {
        // Loader呼び出し元が受け取るデータ
        val asyncResult = AsyncResult<Rss>()

        // 接続＆データをGETする
        val urlConAbs = MyURLConFactory.createInstance(url,null)
        try {
            urlConAbs?.apply {
                openConnection()

                // 処理終了後、クローズする
                addSendHeader("Connection", "close")

                // ------------------------------------------
                // 接続＆GETする
                // ------------------------------------------
                doGet()

                // ----------------------------------------------
                // 通信が成功してれば、取得した文字列をRSS解析する
                // ----------------------------------------------
                if ( responseOK == MyURLConAbs.RESPONSE_OK.OK ) {
                    //val myRssParse = MyRssParse()
                    //val rss = myRssParse.str2rss(this.responseBuffer.toString())
                    val myRssParseAbs = MyRssParseFactory.createInstance(this.responseBuffer.toString())
                    if ( myRssParseAbs != null ) {
                        val rss = myRssParseAbs.analyze()
                        asyncResult.data = rss
                    }
                    else {
                        throw Exception("Unknown Format RSS")
                    }
                }
                // ----------------------------------------------
                // リダイレクトの場合、リダイレクト先に遷移
                // ----------------------------------------------
                else if ( responseOK == MyURLConAbs.RESPONSE_OK.REDIRECT ) {
                    val strURLNext = responseHeaderMap["Location"]?.get(0) ?: ""
                    return accessPeer( URL(strURLNext), null )
                }
            }
        }
        catch ( ex: Exception ) {
            asyncResult.exception = ex
            ex.printStackTrace()
        }

        return asyncResult
    }

    // コールバッククラスに返す前に通る処理
    override fun deliverResult(data: AsyncResult<Rss>?) {
        // 破棄されていたら結果を返さない
        if (isReset || data == null) return

        // 結果をキャッシュする
        cache = data
        super.deliverResult(data)
    }

    // バックグラウンド処理が開始される前に呼ばれる
    override fun onStartLoading() {
        // キャッシュがあるなら、キャッシュを返す
        if (cache != null) {
            deliverResult(cache)
        }

        // コンテンツが変化している場合やキャッシュがない場合には、バックグラウンド処理を行う
        if (takeContentChanged() || cache == null) {
            // 非同期処理を開始
            forceLoad()
        }
    }

    // ローダーが停止する前に呼ばれる処理
    override fun onStopLoading() {
        cancelLoad()
    }

    // ローダーが破棄される前に呼ばれる処理
    override fun onReset() {
        super.onReset()
        onStopLoading()
        cache = null
    }

}
