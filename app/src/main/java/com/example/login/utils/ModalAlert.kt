package com.example.login.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.View.GONE
import com.example.login.R
import com.example.login.databinding.ItemModalBinding
import com.example.login.databinding.ItemProgressBarBinding


object ModalAlert {

    fun show(
        icon: Int,
        tittle: String,
        msg: String,
        actions: List<Action>,
        binding: ItemModalBinding,
    ) {
        binding.imageView.setImageResource(icon)
        binding.textView4.text = tittle
        binding.textView5.text = msg
        actions.forEach { action ->
            when (action.type) {
                Type.LEFT -> {
                    binding.apply {
                        btLeft.visibility = View.VISIBLE
                        btCenter.visibility = GONE
                        btRight.visibility = View.VISIBLE
                        btLeft.text = action.label
                        btLeft.setBackgroundResource(action.color)
                        btLeft.setOnClickListener { action.onClick() }
                    }
                }
                Type.CENTER -> {
                    binding.apply {
                        btLeft.visibility = GONE
                        btCenter.visibility = View.VISIBLE
                        btRight.visibility = GONE
                        btCenter.text = action.label
                        btCenter.setBackgroundResource(action.color)
                        btCenter.setOnClickListener { action.onClick() }
                    }
                }
                Type.RIGHT -> {
                    binding.apply {
                        btLeft.visibility = View.VISIBLE
                        btCenter.visibility = GONE
                        btRight.visibility = View.VISIBLE
                        btRight.text = action.label
                        btRight.setBackgroundResource(action.color)
                        btRight.setOnClickListener { action.onClick() }
                    }
                }
            }
        }
    }

    fun goneModal(binding: ItemModalBinding) {
        binding.root.visibility = GONE
    }

    fun gonePb(binding: ItemProgressBarBinding) {
        binding.root.visibility = GONE
    }

    fun noConnectionInternet(activity: Activity,onRestart:()->Unit,binding: ItemModalBinding) {
        show(
            R.drawable.disconnect_internet,
            activity.getString(R.string.item_disconnection),
            activity.getString(R.string.item_subtitle),
            listOf(
                Action(
                    Type.CENTER,
                    R.drawable.border_radius_blue,
                    activity.getString(R.string.item_retry),
                    onClick = {
                        onRestart()
                    }
                )
            ),binding
        )
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }
}