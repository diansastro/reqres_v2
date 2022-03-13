package com.suitmedia.reqres_v2.data.response

import com.google.gson.annotations.SerializedName
import com.suitmedia.reqres_v2.model.SupportData
import com.suitmedia.reqres_v2.model.GuestData

class GuestResponse(@SerializedName("page") var page: Int? = 0,
                    @SerializedName("per_page") var per_page: Int? = 0,
                    @SerializedName("total") var total: Int? = 0,
                    @SerializedName("total_pages") var total_pages: Int? = 0,
                    @SerializedName("data") var data: ArrayList<GuestData>,
                    @SerializedName("support") var support: SupportData? = null) {
}