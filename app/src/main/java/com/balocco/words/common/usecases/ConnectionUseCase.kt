package com.balocco.words.common.usecases

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class ConnectionUseCase @Inject constructor(
        private val context: Context
) {

    fun isDeviceConnected(): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}