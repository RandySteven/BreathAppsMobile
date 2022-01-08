package com.example.breathingapps2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.breathingapps2.databinding.FragmentCustomRelaxationBinding

class CustomRelaxationFragment : Fragment() {

    private var _binding: FragmentCustomRelaxationBinding ?= null
    private val customRelaxationBinding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomRelaxationBinding.inflate(layoutInflater)
        onClick()
        return _binding?.root
    }

    private fun onClick(){
        customRelaxationBinding?.btnAddInterval.setOnClickListener {

        }
    }

    private fun loadCustom(){

    }
}