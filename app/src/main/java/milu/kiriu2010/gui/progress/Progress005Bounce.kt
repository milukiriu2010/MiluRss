package milu.kiriu2010.gui.progress

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import milu.kiriu2010.milurssviewer.R

class Progress005Bounce: ProgressAbstract() {
    override fun start(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_progress, container, false)

        // 進捗状況を表示するビューをインスタンス化
        val imageView = view.findViewById<ImageView>(R.id.ivProgress)

        // レイアウト・画像サイズ取得
        // エミュレータ(1080x1584) => ButtonNavigationなし
        // エミュレータ(1038x1542) => ButtonNavigationあり
        //  64x 64 => 168x168
        // 120x120 => 315x315
        view.viewTreeObserver.addOnGlobalLayoutListener {
            if (isCalculated == true) return@addOnGlobalLayoutListener
            isCalculated = true
            Log.d(javaClass.simpleName, "W:w[${view.width}]h[${view.height}]/I:w[${imageView.width}]h[${imageView.height}]")

            // レイアウト幅・高さ
            val lw = view.width.toFloat()
            val lh = view.height.toFloat()
            // 画像幅・高さ(1/2に補正)
            imageView.scaleX = 0.5f
            imageView.scaleY = 0.5f
            val iw = imageView.width.toFloat() * imageView.scaleX
            val ih = imageView.height.toFloat() * imageView.scaleY
            Log.d(javaClass.simpleName, "iw[${iw}]ih[${ih}]")

            // スピード
            var vx = iw
            var vy = ih

            // 横はサタンに表示
            // 縦は真ん中に表示(ちょっとずらす)
            imageView.x = 0f
            imageView.y = lh/2 - ih/2 + 3

            // 画像の幅分横に移動
            val duration = 100L
            val animator = imageView.animate()
                    .setDuration(duration)
                    .xBy(vx)
                    .yBy(vy)
            // リピートする
            animator.setListener( object: Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Log.d( javaClass.simpleName, "onAnimationEnd")

                    val ndx = vx
                    val ndy = vy

                    // ------------------------------------------
                    // 壁で反射する
                    // ------------------------------------------
                    // 次の移動で右の壁にヒット
                    // ------------------------------------------
                    if ( (imageView.x+iw+ndx) >= lw ) {
                        vx = -vx
                        //ndx = 2/3 * ndx
                        //ndx = lw - imageView.x - iw
                    }
                    // ------------------------------------------
                    // 次の移動で左の壁にヒット
                    // ------------------------------------------
                    else if ( (imageView.x+ndx) < 0 ) {
                        vx = -vx
                        //ndx = 2/3 * ndx
                        //ndx = -1 * imageView.x
                    }

                    // ------------------------------------------
                    // 次の移動で下の壁にヒット
                    // ------------------------------------------
                    if ( (imageView.y+ih+ndy) >= lh ) {
                        vy = -vy
                        //ndy = 2/3 * ndy
                        //ndy = lh - imageView.y - ih
                    }
                    // ------------------------------------------
                    // 次の移動で上の壁にヒット
                    // ------------------------------------------
                    else if ( (imageView.y+ndy) < 0 ) {
                        vy = -vy
                        //ndy = 2/3 * ndy
                        //ndy = -1 * imageView.y
                    }

                    imageView.animate()
                            .setDuration(duration)
                            .xBy(ndx)
                            .yBy(ndy)
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

            })
        }

        // 経過時間を表示
        updateElapsedTime(view)

        return view
    }
}