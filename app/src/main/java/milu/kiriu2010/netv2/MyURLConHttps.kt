package milu.kiriu2010.netv2

import android.util.Log
import javax.net.ssl.HttpsURLConnection

// HTTPS通信する際、利用されるクラス
class MyURLConHttps: MyURLConAbs() {
    override fun setSocketOption() {
        val conHttps = this.conAbs as? HttpsURLConnection ?: return
        Log.d( this.javaClass.simpleName, "====================" )
        Log.d( this.javaClass.simpleName, "= sslSocketFactory =" )
        Log.d( this.javaClass.simpleName, "====================" )
        conHttps.sslSocketFactory = MySSLSocketFactory(conHttps.sslSocketFactory)
    }
}