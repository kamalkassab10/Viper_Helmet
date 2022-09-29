package com.example.nabtastore

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat.animate
import androidx.navigation.fragment.findNavController
import com.example.nabtastore.databinding.FragmentFristBinding


class frist : Fragment() {

    lateinit var binding: FragmentFristBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFristBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivHelmet
            .animate().apply {
            duration = 1000
            alpha(0.5F)
            scaleXBy(0.5f)
            scaleYBy(0.5f)
            rotationYBy(360f)
            translationYBy(360f)
        }.withEndAction {
                binding.ivHelmet.animate().apply {
                duration = 1000
                alpha(1f)
                scaleXBy(-0.5f)
                scaleYBy(-0.5f)
                rotationXBy(360f)
                translationYBy(-360f)
            }
        }

        var s :String = "Loading"
        binding.tvLoad
            .text=s
        var m:Int
        var z :Int=1000
        for (m in 1..3)
        {

            Handler().postDelayed(
                {
                    s += "."
                    binding.tvLoad.text=s

                }, z.toLong()
            )
            z +=800


        }
        Handler().postDelayed(
            {

                findNavController().navigate(R.id.action_frist_to_secound)
            },3000
        )
    }
}