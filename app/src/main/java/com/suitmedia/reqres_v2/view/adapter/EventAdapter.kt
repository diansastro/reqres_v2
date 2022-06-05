package com.suitmedia.reqres_v2.view.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.suitmedia.reqres_v2.R
import com.suitmedia.reqres_v2.model.EventData
import kotlinx.android.synthetic.main.item_event.view.*

class EventAdapter(var list: List<EventData>): BaseQuickAdapter<EventData, BaseViewHolder>(R.layout.item_event, list) {
    override fun convert(helper: BaseViewHolder?, item: EventData?) {
        helper?.let { h ->
            item?.let { i ->
                if (!i.eventMedia.isNullOrBlank()) {
                    Glide.with(mContext).load(i.eventMedia).transition(DrawableTransitionOptions.withCrossFade()).into(h.itemView.ivEventMedia)
                }

                h.itemView.tvEventName.text = i.eventName
                h.itemView.tvEventDate.text = i.eventDate
                h.itemView.tvEventTime.text = i.eventTime
            }
        }
    }
}