package milu.kiriu2010.gui.progress

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import milu.kiriu2010.milurssviewer.R

// 画像のサイズを1倍⇒3倍に繰り返し変更するアニメーション
class Progress001ScaleCycleInterpolator: ProgressAbstract() {
    override fun start(inflater: LayoutInflater, container: ViewGroup?,
                       savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_progress, container, false)

        // 進捗状況を表示するビューをインスタンス化
        val imageViewProgress = view.findViewById<ImageView>(R.id.ivProgress)
        // アニメーションするリソースを設定する
        imageViewProgress.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_progress_001,null))
        // 進捗状況にする画像をアニメーション化する
        val animation = imageViewProgress.drawable as AnimationDrawable
        // アニメーション開始
        animation.start()

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
            scale.duration = 1000;
            // アニメーションの動きを補完する機能を設定
            scale.interpolator = CycleInterpolator(1.0f)
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