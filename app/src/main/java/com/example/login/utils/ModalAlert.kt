package com.example.login.utils

import android.content.Context
import android.view.View
import android.view.View.GONE
import com.example.login.R
import com.example.login.databinding.ItemModalBinding


object ModalAlert {

    fun show(
        icon: Int,
        tittle: String,
        msg: String,
        actions: List<Action>,
        binding: ItemModalBinding
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

    fun gone(binding: ItemModalBinding) {
        binding.root.visibility = GONE
    }

    fun setModalInternetAlert(context: Context,) {
        show(
            R.drawable.disconnect_internet,
            context.getString(R.string.item_disconnection),
            context.getString(R.string.item_subtitle),
            listOf(
                Action(
                    Type.CENTER,
                    R.drawable.border_radius_blue,
                    context.getString(R.string.item_retry),
                    onClick = {

                    }
                )
            )
        )
    }
}