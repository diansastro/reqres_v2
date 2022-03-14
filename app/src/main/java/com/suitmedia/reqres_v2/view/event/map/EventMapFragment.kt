package com.suitmedia.reqres_v2.view.event.map

import com.suitmedia.reqres_v2.R
import com.suitmedia.reqres_v2.base.BaseMvpFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

open class EventMapFragment: BaseMvpFragment<EventMapPresenter>(), EventMapContract.View {

    @Inject
    override lateinit var presenter: EventMapPresenter

    companion object {
        fun newInstance() = EventMapFragment()
    }

    override fun initPresenterView() {
        presenter.view = this
    }

    override fun injectView() {
        AndroidSupportInjection.inject(this)
    }

    override fun setup() {
        initView()
    }

    override fun getLayout(): Int = R.layout.fragment_event_map

    private fun initView() {

    }
}