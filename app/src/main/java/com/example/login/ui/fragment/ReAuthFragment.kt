package com.example.login.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.login.databinding.FragmentReAuthBinding


class ReAuthFragment : Fragment() {

    private lateinit var binding : FragmentReAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReAuthBinding.inflate(inflater,container,false)

        return binding.root
    }
}