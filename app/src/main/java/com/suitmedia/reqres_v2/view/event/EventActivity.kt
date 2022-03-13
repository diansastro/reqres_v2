package com.suitmedia.reqres_v2.view.event

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaeger.library.StatusBarUtil
import com.suitmedia.reqres_v2.R
import com.suitmedia.reqres_v2.base.BaseMvpActivity
import com.suitmedia.reqres_v2.model.EventData
import com.suitmedia.reqres_v2.view.adapter.EventAdapter
import com.suitmedia.reqres_v2.view.user.UserActivity
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.nav_bar_event.*
import javax.inject.Inject


open class EventActivity: BaseMvpActivity<EventPresenter>(), EventContract.View {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    override lateinit var presenter: EventPresenter

    private val eventData = arrayListOf<EventData>()
    private lateinit var eventAdapter: EventAdapter

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
        initAction()
    }

    override fun getLayout(): Int = R.layout.activity_event

    private fun initView() {
        eventData.add(EventData(1, "Dummy Event 1", "2 April, 2022", ""))
        eventData.add(EventData(2, "Dummy Event 2", "4 May, 2022", ""))
        eventData.add(EventData(3, "Dummy Event 3", "20 June, 2022", ""))
        eventData.add(EventData(4, "Dummy Event 4", "28 August, 2022", ""))

        eventAdapter = EventAdapter(eventData)
        eventAdapter.apply {
            setOnItemClickListener { adapter, view, position ->
                val extras = Bundle()
                val i = Intent(this@EventActivity, UserActivity::class.java)
                extras.putString("userName", intent.getStringExtra("un"))
                extras.putString("guestName", intent.getStringExtra("gn"))
                extras.putString("eventName", eventData[position].eventName!!)
                i.putExtras(extras)
                startActivity(i)
            }
            notifyDataSetChanged()
        }

        rvEventList.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL, false)
            isNestedScrollingEnabled = false
        }
    }

    private fun initAction() {
        ivBackEvent.setOnClickListener { onBackPressed() }
    }
}