package com.suitmedia.reqres_v2.component

import android.app.Dialog
import androidx.fragment.app.DialogFragment
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.FadingCircle
import com.suitmedia.reqres_v2.base.BaseDialogFragment
import com.suitmedia.reqres_v2.R
import kotlinx.android.synthetic.main.loading_default.*

class LoadingProgressDialog: BaseDialogFragment() {

    companion object {
        fun newInstance(): LoadingProgressDialog {
            return LoadingProgressDialog()
        }
    }

    override fun setupDialogStyle(dialog: Dialog) {
    }

    override fun loadArguments() {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DefaultDialog)
    }

    override fun setup() {
        val circle: Sprite = FadingCircle()
        spin_kit.setIndeterminateDrawable(circle)
    }

    override fun getLayout(): Int = R.layout.loading_default
}