package uz.pdp.dictionaryapp.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import uz.pdp.dictionaryapp.utils.NetworkHelper

class InternetReciever : BroadcastReceiver() {
      lateinit var networkHelper: NetworkHelper
    override fun onReceive(context: Context, intent: Intent) {
        networkHelper= NetworkHelper(context)
        if (networkHelper.isNetWorkConnected()){
            Toast.makeText(context, "Network is connected", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(context, "Network is disconnected", Toast.LENGTH_SHORT).show()
        }
    }
}