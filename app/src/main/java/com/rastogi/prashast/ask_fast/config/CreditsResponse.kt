package com.rastogi.prashast.ask_fast.config

import com.google.gson.annotations.SerializedName

class CreditsResponse {

    @SerializedName("cast")
    var castList: List<Cast>? = null
}