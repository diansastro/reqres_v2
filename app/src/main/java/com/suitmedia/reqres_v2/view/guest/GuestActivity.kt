package com.suitmedia.reqres_v2.view.guest

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.jaeger.library.StatusBarUtil
import com.suitmedia.reqres_v2.R
import com.suitmedia.reqres_v2.base.BaseMvpActivity
import com.suitmedia.reqres_v2.data.response.GuestResponse
import com.suitmedia.reqres_v2.model.GuestData
import com.suitmedia.reqres_v2.view.adapter.GuestAdapter
import com.suitmedia.reqres_v2.view.user.UserActivity
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.activity_guest.*
import kotlinx.android.synthetic.main.nav_bar_guest.*
import javax.inject.Inject

open class GuestActivity: BaseMvpActivity<GuestPresenter>(), GuestContract.View {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    override lateinit var presenter: GuestPresenter

    private var guestData = arrayListOf<GuestData>()
    private lateinit var guestAdapter: GuestAdapter

    override fun initPresenterView() {
        presenter.view = this
    }

    override fun injectView() {
        AndroidInjection.inject(this)
    }

    override fun setup() {
        showLoading()
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white_smoke), 0)
        StatusBarUtil.setLightMode(this)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        initAction()
        presenter.execGuestList()
    }

    override fun getLayout(): Int = R.layout.activity_guest

    override fun getGuestList(guestResponse: GuestResponse?) {
        guestData.addAll(guestResponse?.data!!)

        guestAdapter = GuestAdapter(guestData)
        guestAdapter.apply {
            setOnItemClickListener { adapter, view, position ->
                val extras = Bundle()
                val i = Intent(this@GuestActivity, UserActivity::class.java)
                extras.putString("eventName", intent.getStringExtra("en"))
                extras.putString("userName", intent.getStringExtra("un"))
                extras.putString("guestName", guestData[position].first_name)
                extras.putInt("guestId", guestData[position].id!!)
                i.putExtras(extras)
                startActivity(i)
            }
            notifyDataSetChanged()
        }

        val lm = GridLayoutManager(this, 2)
        lm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 2 else 1
            }
        }

        rvGuestList.apply {
            adapter = guestAdapter
            rvGuestList.layoutManager = lm
            isNestedScrollingEnabled = false
        }
        dismissLoading()
    }

    private fun initAction() {
        ivBackGuest.setOnClickListener { onBackPressed() }
    }
}