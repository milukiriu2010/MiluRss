package milu.kiriu2010.loader

// ローダの結果を保持する
// T:正常時に受信するデータの型
data class AsyncResult<T>(
        // ---------------------
        // 正常時：受信するデータ
        //　異常時:NULL
        // ---------------------
        var data: T? = null,
        // ---------------------
        // 正常時：NULL
        //　異常時:通信時に発生した例外
        // ---------------------
        var exception: Exception? = null
)
