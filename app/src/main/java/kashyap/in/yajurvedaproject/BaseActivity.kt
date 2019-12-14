package kashyap.`in`.yajurvedaproject

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewStub
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import kashyap.`in`.yajurvedaproject.receivers.NetworkChangeListener
import kashyap.`in`.yajurvedaproject.receivers.NetworkReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager

abstract class BaseActivity : AppCompatActivity(), NetworkChangeListener {

    lateinit var baseLayout: RelativeLayout
    lateinit var networkReceiver: NetworkReceiver

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        baseLayout =
            LayoutInflater.from(this).inflate(R.layout.activity_base, null) as RelativeLayout
        setStub(layoutResID)
        ButterKnife.bind(this)
        setNetworkReceiver()
    }

    private fun setStub(layoutResID: Int) {
        val viewStub = baseLayout.findViewById<ViewStub>(R.id.container)
        viewStub.layoutResource = layoutResID
        viewStub.inflate()
    }

    private fun setNetworkReceiver() {
        networkReceiver = NetworkReceiver()
        networkReceiver.setNetworkChangeListener(this)
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(networkReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkReceiver)
    }

    override fun onNetworkChanged() {
        if (isNetworkAvailable()) {
            networkAvailable()
        } else {
            networkNotAvailable()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    abstract fun networkAvailable()

    abstract fun networkNotAvailable()

}
