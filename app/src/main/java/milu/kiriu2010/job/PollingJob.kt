package milu.kiriu2010.job

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import milu.kiriu2010.entity.parseRss
import milu.kiriu2010.net.httpGet

// 定期的にRSSに更新がないかチェックするジョブサービス
class PollingJob() : JobService(){

    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        // 通信をするため、非同期的に実行する
        Thread{
            // RSSをダウンロードする
            val response = httpGet("https://www.sbbit.jp/rss/HotTopics.rss")

            if (response != null) {
                // RSSオブジェクトにパースする
                val rss = parseRss(response)

                // プリファレンス
                val prefs = getSharedPreferences("pref_polling", Context.MODE_PRIVATE)

                // 前回更新されたときの時間。保存されていない場合は0を返す
                val lastFetchTime = prefs.getLong("last_publish_time", 0L)

                // 記事が一度は取得済みで、更新されている場合
                if (lastFetchTime > 0 && lastFetchTime < rss.pubDate.time) {
                    // 通知する
                    notifyUpdate(this)
                }

                // 取得時間を保存する
                prefs.edit().putLong("last_publish_time", rss.pubDate.time).apply()
            }
        }.start()

        return true
    }
}
