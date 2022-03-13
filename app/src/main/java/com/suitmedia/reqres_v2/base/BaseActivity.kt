package com.suitmedia.reqres_v2.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.suitmedia.reqres_v2.R
import com.suitmedia.reqres_v2.component.LoadingProgressDialog
import com.suitmedia.reqres_v2.event.LanguageChangedEvent
import com.suitmedia.reqres_v2.objects.RxBus
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.toast
import java.net.HttpURLConnection

abstract class BaseActivity: AppCompatActivity(),
    ErrorView {
    private var backPressed = false
    open var doubleBackExit = false

    private val uiCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        injectView()
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initLayout()
        initSubscription()
        internalSetup()
        setup()
    }

    open fun internalSetup(){}

    abstract fun injectView()

    abstract fun setup()

    abstract fun getLayout():Int

    fun initLayout(){
        if(getLayout() != 0)
            setContentView(getLayout())
    }

    open fun initSubscription() {
        addUiSubscription(RxBus.listen(LanguageChangedEvent::class.java).subscribe {
            recreate()
        })
    }

    fun addUiSubscription(disposable: Disposable){
        uiCompositeDisposable.add(disposable)
    }

    fun clearAllSubscription(){
        uiCompositeDisposable.clear()
    }

    fun isLoading(): Boolean {
        return null != supportFragmentManager
            .findFragmentByTag(LoadingProgressDialog::class.java.canonicalName) as LoadingProgressDialog?
    }

    fun showLoading() {
        Handler().post {
            if (!isDestroyed) {
                supportFragmentManager.executePendingTransactions()
                var progressDialog = supportFragmentManager
                    .findFragmentByTag(LoadingProgressDialog::class.java.canonicalName) as LoadingProgressDialog?

                if (progressDialog == null) {
                    progressDialog = LoadingProgressDialog.newInstance()
                    progressDialog.show(supportFragmentManager, LoadingProgressDialog::class.java.canonicalName)
                }
            }
        }
    }

    fun dismissLoading() {
        Handler().post {
            if (!isDestroyed) {
                supportFragmentManager.executePendingTransactions()
                val progressDialog = supportFragmentManager
                    .findFragmentByTag(LoadingProgressDialog::class.java.canonicalName) as LoadingProgressDialog?
                progressDialog?.dismiss()
            }
        }
    }

    override fun errorScreen(message: String?, code: Int?) {
        dismissLoading()
        if(code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            message?.let { toast(it) }
            forceLogout()
        } else {
            message?.let { toast(it) }
        }
    }

    override fun errorScreen(message: String?) {
        dismissLoading()
        message?.let { toast(it) }
    }

    override fun forceLogout() {
    }

    override fun onDestroy() {
        clearAllSubscription()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (doubleBackExit) {
            if (backPressed) {
                finishAffinity()
            } else {
                backPressed = true
                Toast.makeText(this, getString(R.string.press_back_to_exit), Toast.LENGTH_SHORT).show()
                Handler().postDelayed({ backPressed = false }, 2000)
            }
        } else
            super.onBackPressed()
    }
}