package com.rastogi.prashast.ask_fast.config

class NetworkState {

    companion object {
        val LOADING = "loading"
        val LOADED = "loaded"
        val FAILED = "failed"
    }

    var message: String = ""
    var state: String = LOADING

    constructor(message: String, state: String) {
        this.message = message
        this.state = state
    }


}