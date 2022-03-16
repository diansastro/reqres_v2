package com.suitmedia.reqres_v2.view.event.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.suitmedia.reqres_v2.R
import com.suitmedia.reqres_v2.base.BaseMvpFragment
import com.suitmedia.reqres_v2.model.EventData
import com.suitmedia.reqres_v2.view.adapter.EventAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_event_map.*
import javax.inject.Inject

open class EventMapFragment: BaseMvpFragment<EventMapPresenter>(), EventMapContract.View {

    @Inject
    override lateinit var presenter: EventMapPresenter

    companion object {
        fun newInstance() = EventMapFragment()
    }

    private var index = -1
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

    override fun getLayout(): Int = R.layout.fragment_event_map

    private fun initView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync {
            val googlePlex = CameraPosition.builder().target(LatLng(-6.935176, 107.605055)).zoom(12f).bearing(0f).tilt(45f).build()
            val icoActive = bitmapDescriptorFromVector(context, R.drawable.ic_marker_selected)
            val icoInActive = bitmapDescriptorFromVector(context, R.drawable.ic_marker_unselected)

            it.mapType = GoogleMap.MAP_TYPE_NORMAL
            it.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null)
            it.setOnMarkerClickListener { true }

            eventData.add(EventData(1, "Dummy Event 1", "2 April, 2022", "", -6.956194, 107.554126))
            eventData.add(EventData(2, "Dummy Event 2", "4 May, 2022", "", -6.949736, 107.562543))
            eventData.add(EventData(3, "Dummy Event 3", "20 June, 2022", "", -6.935144, 107.601300))
            eventData.add(EventData(4, "Dummy Event 4", "28 August, 2022", "", -6.936788, 107.611300))

            eventData.forEach { i ->
                it.addMarker(MarkerOptions().position(LatLng(i.lat!!, i.log!!)).title(i.eventName!!).icon(icoInActive))
            }
            eventAdapter = EventAdapter(eventData)
            eventAdapter.apply {
                setOnItemClickListener { adapter, view, position ->
                    index = position
                    for (index in 0..data.size ) {
                        if (index == position) it.addMarker(MarkerOptions().position(LatLng(eventData[position].lat!!, eventData[position].log!!)).title(eventData[position].eventName!!).icon(icoActive))
                    }
                }
                notifyDataSetChanged()
            }

            rvEventMap.apply {
                adapter = eventAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                isNestedScrollingEnabled = false
            }
        }
    }

    private fun bitmapDescriptorFromVector(context: Context?, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}