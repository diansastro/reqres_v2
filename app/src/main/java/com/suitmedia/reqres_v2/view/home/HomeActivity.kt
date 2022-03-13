package com.suitmedia.reqres_v2.view.home

import android.content.Intent
import android.widget.Toast
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
import java.util.*
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
        addUiSubscription(etInputPalindrome.textChanges().observeOn(AndroidSchedulers.mainThread()).subscribe {
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

        btCkPalindrome.setOnClickListener {
            val text = etInputPalindrome.text.toString()
            if (isPalindrome(text)) makeText("isPalindrome")
            else makeText("not palindrome")
        }
    }

    private fun checkMandatory() {
        btNext.isEnabled = (etInputName.text.isNotEmpty())
        btCkPalindrome.isEnabled = (etInputPalindrome.text.isNotEmpty())
    }

    private fun makeText(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isPalindrome(string: String): Boolean {
        val str = string.lowercase(Locale.getDefault())
            .replace("""[\W+]""".toRegex(), "")
        return str == str.reversed()
    }
}