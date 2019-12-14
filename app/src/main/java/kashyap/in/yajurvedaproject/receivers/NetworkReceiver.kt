package kashyap.`in`.yajurvedaproject.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Kashyap Bhat on 2019-12-14.
 */

class NetworkReceiver : BroadcastReceiver() {

    private lateinit var networkChangeListener: NetworkChangeListener

    override fun onReceive(p0: Context?, p1: Intent?) {
        networkChangeListener.onNetworkChanged()
    }

    fun setNetworkChangeListener(networkChangeListener: NetworkChangeListener) {
        this.networkChangeListener = networkChangeListener
    }
}