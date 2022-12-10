package com.example.smartportfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartportfolio.databinding.FragmentIntroTwoBinding

// 인트로 읽기모드 프래그먼트
class IntroTwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_intro_two, container, false)
        val binding = FragmentIntroTwoBinding.inflate(inflater, container, false)

        binding.userName.setText(IntroOneFragment.userName)

        return binding.root
    }

}