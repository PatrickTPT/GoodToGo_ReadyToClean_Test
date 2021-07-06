package com.example.newfunctest_recycle

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

//檢查網路連線
object Constants {

    const val apikey: String ="c8d6e0eb46"
    //const val secretKeys = "n2kuRxmJ9cTbxqmo"
    const val BASE_URL: String = "https://app.goodtogo.tw/"
    const val BASE_URL2 = "https://jsonplaceholder.typicode.com/" //測試用的fake URL

    fun isNetworkAvailable(context: Context) : Boolean{
        val connectivityManager = context.
        getSystemService(Context.CONNECTIVITY_SERVICE) as
                ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }

        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo !=null && networkInfo.isConnectedOrConnecting
        }


    }
}