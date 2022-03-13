package com.suitmedia.reqres_v2.view.home

import android.content.Intent
import com.jaeger.library.StatusBarUtil
import com.jakewharton.rxbinding3.widget.textChanges
import com.suitmedia.reqres_v2.R
import com.suitmedia.reqres_v2.base.BaseMvpActivity
import com.suitmedia.reqres_v2.view.user.UserActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

open class HomeActivity:  BaseMvpActivity<HomePresenter>(), HomeContract.View, HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    override lateinit var presenter: HomePresenter

    override var doubleBackExit: Boolean = true

    override fun initPresenterView() {
        presenter.view = this
    }

    override fun injectView() {
        AndroidInjection.inject(this)
    }

    override fun setup() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null)
        StatusBarUtil.setLightMode(this)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        checkMandatory()
        initAction()
    }

    override fun getLayout(): Int = R.layout.activity_home

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun initSubscription() {
        addUiSubscription(etInputName.textChanges().observeOn(AndroidSchedulers.mainThread()).subscribe {
            if(it.isNotEmpty()){
                checkMandatory()
            }
        })
        super.initSubscription()
    }

    private fun initAction() {
        btNext.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("userName", etInputName.text.toString())
            startActivity(intent)
        }
    }

    private fun checkMandatory() {
        btNext.isEnabled = (etInputName.text.isNotEmpty())
    }
}