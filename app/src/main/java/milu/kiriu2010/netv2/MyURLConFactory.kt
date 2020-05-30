package milu.kiriu2010.netv2

import android.util.Log
import java.lang.Exception
import java.net.URL
import java.net.URLConnection

// ----------------------------------------------------
// HTTP/HTTPS通信するオブジェクト生成
// URLの値によって、HTTP or HTTPS用のいづれかを生成する
// ----------------------------------------------------
// 2020.05.30  判定できない場合、NULLではなくExceptionを発行
// ----------------------------------------------------
class MyURLConFactory {

    companion object {
        private val TAG = "MyURLConFactory"

        @Throws(Exception::class)
        fun createInstance( url: URL, myURLConAbsCmp: MyURLConAbs? ): MyURLConAbs {
            Log.d( TAG, "================================" )
            Log.d( TAG, "URL protocol[" + url.protocol + "]" )
            Log.d( TAG, "URL host[" + url.host + "]" )
            Log.d( TAG, "URL port[" + url.port + "]" )
            Log.d( TAG, "URL path[" + url.path + "]" )
            Log.d( TAG, "================================" )

            // ------------------------------------------
            // 接続オブジェクトがある場合、
            // 接続先プロトコル/ホスト/ポートを比較し、
            // 同じ場合、
            // コネクションを再利用したいため、
            // オブジェクトをコピーしたものを返す
            // -----------------------------------------
            myURLConAbsCmp?.let {
                if (
                        ( url.protocol.equals(it.url.protocol) ) and
                        ( url.host.equals(it.url.host) ) and
                        (url.port.equals(it.url.port))
                ) {
                    val myURLConAbsCopy = it.clone() as MyURLConAbs
                    myURLConAbsCopy.url = url
                    return myURLConAbsCopy
                }
            }

            // ------------------------------------------
            // 接続オブジェクトがない場合、
            // 接続先プロトコルを元に生成
            // -----------------------------------------
            val myURLConAbs: MyURLConAbs =
                if ( "http".equals(url.protocol) ) {
                    MyURLConHttp()
                }
                else if ( "https".equals(url.protocol) ) {
                    MyURLConHttps()
                }
                else{
                    throw Exception("not found protocol")
                }

            myURLConAbs.url = url

            return myURLConAbs
        }
    }
}