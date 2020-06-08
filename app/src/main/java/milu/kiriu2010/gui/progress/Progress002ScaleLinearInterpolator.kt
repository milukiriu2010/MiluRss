package milu.kiriu2010.gui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import milu.kiriu2010.milurssviewer.R

class Progress002ScaleLinearInterpolator: ProgressAbstract() {
    override fun start(inflater: LayoutInflater, container: ViewGroup?,
                       savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_progress, container, false)

        // 進捗状況を表示するビューをインスタンス化
        val imageViewProgress = view.findViewById<ImageView>(R.id.ivProgress)
        /*
        アニメーションを利用しないのでコメントアウト
        // 進捗状況にする画像をアニメーション化する
        //val animation = imageViewProgress.drawable as AnimationDrawable
        // アニメーション開始
        //animation.start()
        */

        imageViewProgress.let{
            val scale = ScaleAnimation(
                    1.0f,
                    3.0f,
                    1.0f,
                    3.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
            )

            // アニメーションを行う時間を設定
            scale.duration = 500;
            // アニメーションの動きを補完する機能を設定
            scale.interpolator = LinearInterpolator()
            // アニメーションのリピート回数
            scale.repeatCount = -1
            // アニメーションのスタート
            it.startAnimation(scale)
        }

        // 経過時間を表示
        updateElapsedTime(view)

        return view
    }
}