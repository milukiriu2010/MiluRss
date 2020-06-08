package milu.kiriu2010.gui.progress

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import milu.kiriu2010.milurssviewer.R

// NWアクセスしているときに
// アニメーション表示するフラグメント
class ProgressFragment : Fragment() {

    lateinit var progressAbs: ProgressAbstract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // キーボードを閉じる
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(container?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

        // 表示するアニメーションをランダムに決定する
        val randomInteger = (1..ProgressID.values().size).shuffled().first()
        progressAbs = ProgressFactory.createInstance(randomInteger, resources)
        return progressAbs.start(inflater,container,savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        progressAbs.removeHandler()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                ProgressFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}