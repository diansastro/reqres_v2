package com.suitmedia.reqres_v2.view.guest

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var currentPage = 1
    private var totalAvailablePages = 1

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
        presenter.execOfflineGuest()
        initData()
    }

    override fun getLayout(): Int = R.layout.activity_guest

    override fun getGuestListPaging(guestResponse: GuestResponse?) {
        guestData.addAll(guestResponse?.data!!)
        totalAvailablePages = guestResponse.total_pages!!
        currentPage = guestResponse.page!!

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

    override fun onSuccessOfflineLoad() {

    }

    private fun initData() {
        if (!checkForInternet(this)) {
            loadOfflineData()
        } else {
            initView()
            initScroll()
        }
    }

    private fun initView() {
        if (currentPage == 1 ) presenter.execGuestListPaging(currentPage)
    }

    private fun loadOfflineData() {
        guestData.addAll(presenter.guestRepository.guestData)
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
    }

    private fun initScroll() {
        rvGuestList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rvGuestList.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1
                        presenter.execGuestListPaging(currentPage)
                        toggleLoading()
                    }
                }
            }
        })
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION") return networkInfo.isConnected
        }
    }

    private fun toggleLoading() {
        if (loadMoreProgress.isShown) {
            loadMoreProgress.visibility = View.GONE
        } else {
            loadMoreProgress.visibility = View.VISIBLE
        }
    }

    private fun initAction() {
        ivBackGuest.setOnClickListener { onBackPressed() }
    }
}