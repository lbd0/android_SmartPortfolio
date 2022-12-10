package com.example.smartportfolio

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.smartportfolio.databinding.FragmentMainResumeBinding
import com.example.smartportfolio.databinding.MainResumeRecyclerviewBinding

// 이력 화면
class MainResumeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main_resume, container, false)

        val binding = FragmentMainResumeBinding.inflate(inflater, container, false)

        val contents = mutableListOf<String>("동화고등학교 졸업", "한림대학교 입학")
        val contents_sub = mutableListOf<String>("2020.1.26", "2020.3.1")

        binding.mainResumeRecyclerview.layoutManager = LinearLayoutManager(activity)
        binding.mainResumeRecyclerview.adapter = MyAdapterResume(contents, contents_sub)

        if(IntroActivity.mode == IntroActivity.READMODE) binding.mainResumeFab.visibility = View.GONE
        binding.mainResumeFab.setOnClickListener {
            Log.d("bada", "click")
        }


        return binding.root
    }
}

class MyViewHolderResume(val binding: MainResumeRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapterResume(val contents: MutableList<String>, val contents_sub: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolderResume(MainResumeRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolderResume).binding

        binding.mainResumeData.text = contents[position]
        binding.mainResumeSubdata.text = contents_sub[position]

    }

    override fun getItemCount(): Int {
        return contents.size
    }
}

