package com.example.workflow.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.workflow.MainActivity


class InternetChecker {

    companion object{
        fun checkConnectivity(context: Context? ) : Boolean{
            if(context != null){
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val networkCapabilities = cm.activeNetwork?: return false
                val networkInfo = cm.getNetworkCapabilities(networkCapabilities)?: return false

                return networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            }

            return false;

        }
    }


}