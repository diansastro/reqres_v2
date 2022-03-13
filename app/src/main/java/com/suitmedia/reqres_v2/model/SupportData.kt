package com.suitmedia.reqres_v2.model

import com.google.gson.annotations.SerializedName

class SupportData(@SerializedName("url") var url: String? = "",
                  @SerializedName("text") var text: String? = "") {
}