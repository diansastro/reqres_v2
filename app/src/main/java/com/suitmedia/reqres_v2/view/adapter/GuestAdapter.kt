package com.suitmedia.reqres_v2.view.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.suitmedia.reqres_v2.R
import com.suitmedia.reqres_v2.model.GuestData
import kotlinx.android.synthetic.main.item_guest.view.*

class GuestAdapter(var list: List<GuestData>): BaseQuickAdapter<GuestData, BaseViewHolder>(R.layout.item_guest, list) {
    override fun convert(helper: BaseViewHolder?, item: GuestData?) {
        val radius = mContext.resources.getDimensionPixelSize(R.dimen.corner_radius)
        helper?.let { h ->
            item?.let { i ->
                if (!i.avatar.isNullOrBlank()) {
                    Glide.with(mContext).load(i.avatar)
                        .transform(CenterCrop(), RoundedCorners(radius))
                        .transition(DrawableTransitionOptions.withCrossFade()).into(h.itemView.ivGuest)
                }

                h.itemView.tvGuestName.text = i.first_name.plus(i.last_name)
            }
        }
    }
}