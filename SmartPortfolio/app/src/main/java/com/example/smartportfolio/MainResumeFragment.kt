package com.example.smartportfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartportfolio.databinding.FragmentMainResumeBinding

// 이력 화면
class MainResumeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_resume, container, false)

        val binding = FragmentMainResumeBinding.inflate(layoutInflater)

        val contents = mutableListOf<String>("동화고등학교 졸업", "한림대학교 입학")
        val contents_sub = mutableListOf<String>("2020.1.26", "2020.3.1")
    }


}