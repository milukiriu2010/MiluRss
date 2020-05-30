package milu.kiriu2010.milurssviewer.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import milu.kiriu2010.entity.GenreData
import milu.kiriu2010.entity.Rss
import milu.kiriu2010.entity.URLData
import milu.kiriu2010.id.BundleID
import milu.kiriu2010.milurssviewer.R
import java.net.URL

// 選択されたジャンルに対応する
// URL一覧を表示するフラグメント
class URLLstFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView

    private lateinit var genreData: GenreData

    // URLをタップしたときのコールバックインターフェース
    interface OnURLSelectListener {
        fun onSelectedURL( urlData: URLData )
    }

    // ---------------------------------------------------------
    // URL一覧フラグメントを生成
    // ---------------------------------------------------------
    companion object {
        // 2chをデフォルト表示するジャンルとしている
        fun newInstance( genreData: GenreData = GenreData( 1, "2ch" ) ): Fragment {
            val fragmentURLLst = URLLstFragment()

            // URL一覧フラグメントに渡すデータをセット
            val args = Bundle()
            args.putParcelable( BundleID.ID_GENRE.id, genreData )
            fragmentURLLst.arguments = args

            return fragmentURLLst
        }
    }

    // ---------------------------------------------------------
    // このフラグメントがアクティビティに配置されたとき呼ばれる
    // ---------------------------------------------------------
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d( javaClass.simpleName, "onAttach" )

        // アクティビティがコールバックを実装していなかったら例外を投げる
        if ( context !is OnURLSelectListener ) {
            throw RuntimeException("$context must implement OnURLSelectListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(javaClass.simpleName, "onDetach")
    }

    // ---------------------------------------------------------
    // 呼び出し時に渡される引数から指定されたジャンルを取り出す
    // ---------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(javaClass.simpleName, "onCreate")

        val args = this.arguments ?: return
        this.genreData = args.getParcelable(BundleID.ID_GENRE.id)!!
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(javaClass.simpleName, "onDestroy")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        Log.d( javaClass.simpleName, "onCreateView" )

        // XMLからURL一覧を表示するビューを生成
        val view = inflater.inflate( R.layout.fragment_rss_urllst, container, false )
        recyclerView = view.findViewById(R.id.rvURL)

        // URL一覧を縦方向に並べて表示
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        // コンテキストのnullチェック
        val ctx = context ?: return view

        // URL一覧を表示するためのアダプタ
        val adapter = URLLstAdapter( ctx, URLData.loadURLData(genreData) ) { urlData ->
            // タップされたらコールバックを呼ぶ
            // コールバックにタップされたURLDataオブジェクトを渡す
            // ------------------------------------------
            // つまるところ、このフラグメントの親Activityである
            // URLLstActivityを呼び足している
            ( ctx as OnURLSelectListener ).onSelectedURL(urlData)
        }
        recyclerView.adapter = adapter

        // 区切り線を入れる
        // https://qiita.com/morimonn/items/035b1d85fec56e64f3e1
        val itemDecoration = DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(javaClass.simpleName, "onDestroyView")
    }



}