package com.example.nabtastore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.nabtastore.databinding.FragmentSecoundBinding

class secound : Fragment() {
    lateinit var binding: FragmentSecoundBinding
    lateinit var left_anim :Animation
    lateinit var right_anim :Animation


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecoundBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        left_anim =AnimationUtils.loadAnimation(context,R.anim.anim_mohamed)
        binding.linyarMohamed.animation = left_anim
        binding.linyarKamal.animation = left_anim


    }


}