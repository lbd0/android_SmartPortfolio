package com.example.smartportfolio

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartportfolio.databinding.FragmentIntroOneBinding

// 인트로 쓰기모드 프래그먼트
class IntroOneFragment : Fragment() {
    companion object {
        var userName:String? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_intro_one, container, false)
        val binding = FragmentIntroOneBinding.inflate(inflater, container, false)

        binding.introEdit.hint = "${userName ?: "name"}"

        return binding.root
    }

}