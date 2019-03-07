package com.rastogi.prashast.ask_fast.config

import com.google.gson.annotations.SerializedName

class Cast {
    @SerializedName("credit_id")
     var id: String? = null

    @SerializedName("name")
     var actorName: String? = null

    @SerializedName("character")
     var characterName: String? = null
}