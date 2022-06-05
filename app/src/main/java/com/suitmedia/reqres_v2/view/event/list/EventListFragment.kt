package com.suitmedia.reqres_v2.view.event.list

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suitmedia.reqres_v2.R
import com.suitmedia.reqres_v2.base.BaseMvpFragment
import com.suitmedia.reqres_v2.model.EventData
import com.suitmedia.reqres_v2.view.adapter.EventAdapter
import com.suitmedia.reqres_v2.view.user.UserActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_event_list.*
import javax.inject.Inject

open class EventListFragment: BaseMvpFragment<EventListPresenter>(), EventListContract.View {

    @Inject
    override lateinit var presenter: EventListPresenter

    companion object {
        fun newInstance() = EventListFragment()
    }

    private val eventData = arrayListOf<EventData>()
    private lateinit var eventAdapter: EventAdapter

    override fun initPresenterView() {
        presenter.view = this
    }

    override fun injectView() {
        AndroidSupportInjection.inject(this)
    }

    override fun setup() {
        initView()
    }

    override fun getLayout(): Int = R.layout.fragment_event_list

    private fun initView() {
        eventData.add(EventData(1, "Dummy Event 1", "2 April, 2022", "9.00 AM", null, 0.0, 0.0))
        eventData.add(EventData(2, "Dummy Event 2", "4 May, 2022", "10.20 AM", null, 0.0, 0.0))
        eventData.add(EventData(3, "Dummy Event 3", "20 June, 2022", "14.30 PM", null, 0.0, 0.0))
        eventData.add(EventData(4, "Dummy Event 4", "28 August, 2022", "17.00 PM", null, 0.0, 0.0))

        eventAdapter = EventAdapter(eventData)
        eventAdapter.apply {
            setOnItemClickListener { adapter, view, position ->
                val extras = Bundle()
                val i = Intent(activity, UserActivity::class.java)
                extras.putString("userName", activity?.intent?.getStringExtra("un"))
                extras.putString("guestName", activity?.intent?.getStringExtra("gn"))
                extras.putString("eventName", eventData[position].eventName!!)
                i.putExtras(extras)
                startActivity(i)
            }
            notifyDataSetChanged()
        }

        rvEventList.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isNestedScrollingEnabled = false
        }
    }
}