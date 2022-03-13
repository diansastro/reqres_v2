package com.suitmedia.reqres_v2.view.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.jaeger.library.StatusBarUtil
import com.suitmedia.reqres_v2.R
import com.suitmedia.reqres_v2.base.BaseMvpActivity
import com.suitmedia.reqres_v2.view.event.EventActivity
import com.suitmedia.reqres_v2.view.guest.GuestActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_user.*
import javax.inject.Inject

open class UserActivity: BaseMvpActivity<UserPresenter>(), UserContract.View, HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    override lateinit var presenter: UserPresenter

    override fun initPresenterView() {
        presenter.view = this
    }

    override fun injectView() {
        AndroidInjection.inject(this)
    }

    override fun setup() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white_smoke), 0)
        StatusBarUtil.setLightMode(this)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        initView()
    }

    override fun getLayout(): Int = R.layout.activity_user

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    private fun initView() {
        val intent = intent
        val extras = intent.extras
        val u = extras?.getString("userName")
        val e = extras?.getString("eventName")
        val g = extras?.getString("guestName")
        val id = extras?.getInt("guestId")

        tvUserName.text = u
        btEvent.text = e ?: getString(R.string.choose_event)
        btGuest.text = g ?: getString(R.string.choose_guest)

        btEvent.setOnClickListener {
            val bd = Bundle()
            val i = Intent(this, EventActivity::class.java)
            bd.putString("un", intent.getStringExtra("userName"))
            bd.putString("en", intent.getStringExtra("eventName"))
            bd.putString("gn", intent.getStringExtra("guestName"))
            i.putExtras(bd)
            startActivity(i)
        }

        btGuest.setOnClickListener {
            val b = Bundle()
            val i = Intent(this, GuestActivity::class.java)
            b.putString("un", intent.getStringExtra("userName"))
            b.putString("en", intent.getStringExtra("eventName"))
            b.putString("gn", intent.getStringExtra("guestName"))
            i.putExtras(b)
            startActivity(i)
        }

        if (id != null && id != 0) {
            if ((id%2 == 0) && (id%3 == 0)) {makeText("iOS")}
            else if (id%2 == 0) {makeText("Blackberry")}
            else if (id%3 == 0) {makeText("Android")}
            else {makeText("Blackberry, Android, iOS")}
        }
    }

    private fun makeText(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}