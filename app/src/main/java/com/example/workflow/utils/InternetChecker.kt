package com.example.workflow.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities



class InternetChecker private constructor() {

    companion object{

        private var instance : InternetChecker? = null
        private var context : Context? = null
        fun getInstance(contextValue: Context?) : InternetChecker {
            if(instance == null && contextValue != null){
                context = contextValue
                instance = InternetChecker()
                return instance as InternetChecker
            }else if(instance != null){
                return instance as InternetChecker
            }

            throw Error("There is not any instance of InternetChecker")
        }
    }

    fun checkConnectivity( ) : Boolean{
        if(context != null){
            val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkCapabilities = cm.activeNetwork?: return false
            val networkInfo = cm.getNetworkCapabilities(networkCapabilities)?: return false

            return networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }

        return false;

    }


}