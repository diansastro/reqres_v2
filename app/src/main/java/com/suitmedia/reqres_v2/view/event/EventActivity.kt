package com.suitmedia.reqres_v2.view.event

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jaeger.library.StatusBarUtil
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueRootTabHistoryStrategy
import com.suitmedia.reqres_v2.R
import com.suitmedia.reqres_v2.base.BaseFragment
import com.suitmedia.reqres_v2.base.BaseMvpActivity
import com.suitmedia.reqres_v2.view.event.list.EventListFragment
import com.suitmedia.reqres_v2.view.event.map.EventMapFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.nav_bar_event.*
import java.util.ArrayList
import javax.inject.Inject

open class EventActivity: BaseMvpActivity<EventPresenter>(), EventContract.View, HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    override lateinit var presenter: EventPresenter

    private lateinit var fragNavController: FragNavController

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
        initTab()
        initView()
        initAction()
    }

    override fun getLayout(): Int = R.layout.activity_event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    private fun initTab(){
        ViewCompat.setOnApplyWindowInsetsListener(mainFrame,
            object : OnApplyWindowInsetsListener {
                override fun onApplyWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
                    var insets = insets
                    insets = ViewCompat.onApplyWindowInsets(v, insets)

                    if (insets.isConsumed) {
                        return insets
                    }
                    var consumed = false
                    var i = 0
                    val count = mainFrame.childCount
                    while (i < count) {
                        ViewCompat.dispatchApplyWindowInsets(mainFrame.getChildAt(i), insets)
                        if (insets.isConsumed) {
                            consumed = true
                        }
                        i++
                    }
                    return if (consumed) insets.consumeSystemWindowInsets() else insets
                }
            })

        fragNavController = FragNavController(supportFragmentManager, R.id.mainFrame)
        fragNavController.navigationStrategy = UniqueRootTabHistoryStrategy(object: FragNavSwitchController {
            override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                fragNavController.switchTab(index, transactionOptions)
            }
        })
        fragNavController.fragmentHideStrategy = FragNavController.HIDE
        fragNavController.createEager = true
        val fragments = ArrayList<BaseFragment>()
        fragments.add(EventListFragment.newInstance())
        fragments.add(EventMapFragment.newInstance())
        fragNavController.rootFragments = fragments
        refreshTab()
    }

    private fun initView() {
        ivListEvent.visibility = View.INVISIBLE
        ivListEvent.isEnabled = false
    }

    private fun initAction() {
        ivMapEvent.setOnClickListener { selectTab(TabList.Map) }
        ivListEvent.setOnClickListener { selectTab(TabList.List) }
        ivBackEvent.setOnClickListener { onBackPressed() }
    }

    private fun refreshTab() {
        when(fragNavController.currentStackIndex) {
            TabList.List -> {
                ivListEvent.visibility = View.INVISIBLE
                ivListEvent.isEnabled = false
                ivMapEvent.visibility = View.VISIBLE
                ivMapEvent.isEnabled = true
            }
            TabList.Map -> {
                ivListEvent.visibility = View.VISIBLE
                ivListEvent.isEnabled = true
                ivMapEvent.visibility = View.INVISIBLE
                ivMapEvent.isEnabled = false
            }
        }
    }

    private fun selectTab(tabIndex: Int) {
        if (tabIndex >= 0 && null != fragNavController.rootFragments && tabIndex < fragNavController.rootFragments!!.size) {
            fragNavController.switchTab(tabIndex)
            fragNavController.switchTab(tabIndex)
            refreshTab()
        }
    }
}