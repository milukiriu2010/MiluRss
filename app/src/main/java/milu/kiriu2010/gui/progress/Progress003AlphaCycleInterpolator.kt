package milu.kiriu2010.gui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageView
import android.widget.TextView
import milu.kiriu2010.milurssviewer.R

class Progress003AlphaCycleInterpolator: ProgressAbstract() {
    override fun start(inflater: LayoutInflater, container: ViewGroup?,
                       savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_progress, container, false)

        // 進捗状況を表示するビューをインスタンス化
        val imageViewProgress = view.findViewById<ImageView>(R.id.ivProgress)

        imageViewProgress.let{
            val animationSet = AnimationSet(true)
            // フェードアウト・フェードイン
            animationSet.addAnimation(AlphaAnimation(0.0f,1.0f))
            // アニメーションを行う時間を設定
            animationSet.duration = 20000
            //animationSet.duration = 1000
            // アニメーションの動きを補完する機能を設定
            animationSet.interpolator = CycleInterpolator(1.0f)
            // アニメーションのリピート回数
            animationSet.repeatCount = -1

            // 画像を2倍にする
            it.scaleX = 2.0f
            it.scaleY = 2.0f

            // アニメーションのスタート
            it.startAnimation(animationSet)

        }

        // 経過時間を表示
        updateElapsedTime(view)

        return view
    }
}