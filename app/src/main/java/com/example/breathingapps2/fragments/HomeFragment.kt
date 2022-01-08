package com.example.breathingapps2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.breathingapps2.R
import com.example.breathingapps2.databinding.FragmentHomeBinding
import org.jetbrains.anko.find

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding ?= null
    private val homeBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        onClick()

        return _binding?.root
    }

    private fun onClick(){
        homeBinding?.buttonCustom?.setOnClickListener {
            changeFragment(CustomRelaxationFragment())
        }

        homeBinding?.buttonDaily?.setOnClickListener {

        }

        homeBinding?.buttonMusic?.setOnClickListener {
            changeFragment(MusicFragment())
        }

        homeBinding?.buttonVideo?.setOnClickListener {
            changeFragment(VideoFragment())
        }
    }

    private fun changeFragment(fragment : Fragment){
        var fr = fragmentManager?.beginTransaction()
        fr?.replace(R.id.frame_main, fragment)
        fr?.commit()
    }

}