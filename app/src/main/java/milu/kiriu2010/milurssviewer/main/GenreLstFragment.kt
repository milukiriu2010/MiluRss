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
import android.widget.TextView
import milu.kiriu2010.entity.GenreData
import milu.kiriu2010.milurssviewer.R

class GenreLstFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView

    // ジャンルをタップしたときのコールバックインターフェース
    interface OnGenreSelectListener {
        fun onSelectedGenre( genreData: GenreData )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    // ---------------------------------------------------------
    // このフラグメントがアクティビティに配置されたとき呼ばれる
    // ---------------------------------------------------------
    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d( javaClass.simpleName, "" )
        Log.d( javaClass.simpleName, "onAttach" )
        Log.d( javaClass.simpleName, "========" )

        // アクティビティがコールバックを実装していなかったら例外を投げる
        if ( context !is OnGenreSelectListener ) {
            throw RuntimeException("$context must implement OnGenreSelectListener")
        }
    }

    // ---------------------------------------------------------
    // 表示するビューを生成する
    // ---------------------------------------------------------
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        Log.d( javaClass.simpleName, "" )
        Log.d( javaClass.simpleName, "onCreateView" )
        Log.d( javaClass.simpleName, "============" )

        val view = inflater.inflate( R.layout.fragment_rss_genrelst, container, false )
        recyclerView = view.findViewById(R.id.rvGenre)

        // ジャンルリストを縦方向に並べて表示
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        // コンテキストのnullチェック
        val ctx = context ?: return view

        // ジャンル一覧を表示するためのアダプタ
        val adapter = GenreLstAdapter( ctx, GenreData.loadGenreData() ) { genreData ->
            // タップされたらコールバックを呼ぶ
            // タップされたジャンル情報を渡す
            // URLLstActivity.onSelectedGenreが呼び出される
            ( ctx as OnGenreSelectListener ).onSelectedGenre(genreData)
        }
        recyclerView.adapter = adapter

        // 区切り線を入れる
        // https://qiita.com/morimonn/items/035b1d85fec56e64f3e1
        val itemDecoration = DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        return view
    }

    companion object {
        fun newInstance() =
                GenreLstFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

}