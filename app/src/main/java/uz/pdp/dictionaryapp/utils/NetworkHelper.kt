package uz.pdp.dictionaryapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkHelper constructor(private val context: Context) {
    fun isNetWorkConnected():Boolean{
        var result=false
        val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            var networkCapabilities=connectivityManager.activeNetwork ?: return false
            var activeNetwork=connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result= when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
                else->false
            }
        } else{
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result= when(type){
                        ConnectivityManager.TYPE_WIFI->true
                        ConnectivityManager.TYPE_MOBILE->true
                        ConnectivityManager.TYPE_ETHERNET->true
                        else ->false
                    }

                }
            }
        }
        return result
    }
}